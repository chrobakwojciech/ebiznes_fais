package controllers.api

import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.util.{Credentials, PasswordHasherRegistry}
import com.mohiva.play.silhouette.api.{LoginEvent, LoginInfo, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers.{CommonSocialProfileBuilder, CredentialsProvider, SocialProvider, SocialProviderRegistry}
import javax.inject.Inject
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.auth.{LoginInfoDAO, UserService}
import utils.auth.{JsonErrorHandler, JwtEnv}

import scala.concurrent.{ExecutionContext, Future}

case class SignUp(firstName: String,
                  lastName: String,
                  email: String,
                  password: String
                 )

object SignUp {
  implicit val userFormat = Json.format[SignUp]
}

case class SignIn(email: String,
                  password: String
                 )

object SignIn {
  implicit val sigInFormat = Json.format[SignIn]
}


class AuthenticationApiController @Inject()(cc: ControllerComponents,
                                            userService: UserService,
                                            errorHandler: JsonErrorHandler,
                                            silhouetteJwt: Silhouette[JwtEnv],
                                            authInfoRepository: AuthInfoRepository,
                                            passwordHasherRegistry: PasswordHasherRegistry,
                                            loginInfoDAO: LoginInfoDAO,
                                            socialProviderRegistry: SocialProviderRegistry,
                                            credentialsProvider: CredentialsProvider)
                                           (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  val jwtAuthService: AuthenticatorService[JWTAuthenticator] = silhouetteJwt.env.authenticatorService

  def signUp() = Action(parse.json).async { implicit request =>
    val body = request.body
    body.validate[SignUp].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      userData => {
        val loginInfo = LoginInfo(CredentialsProvider.ID, userData.email)

        userService.getByEmail(userData.email).flatMap {
          case Some(user) => Future.successful(BadRequest(Json.obj("message" -> "User already exist")))
          case None => {
            for {
              _ <- userService.saveOrUpdate(userData.firstName, userData.lastName, userData.email, loginInfo)
              authInfo = passwordHasherRegistry.current.hash(userData.password)
              _ <- authInfoRepository.add(loginInfo, authInfo)

              authenticator <- jwtAuthService.create(loginInfo)
              token <- jwtAuthService.init(authenticator)
              result <- jwtAuthService.embed(token, Ok(Json.obj("message" -> "User created", "token" -> token)))
            } yield {
              result
            }
          }
        }
      }
    )
  }

  def signIn() = Action(parse.json).async { implicit request =>
    val body = request.body
    body.validate[SignIn].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      credentials => {
        credentialsProvider.authenticate(credentials = Credentials(credentials.email, credentials.password))
          .flatMap { loginInfo =>
            userService.retrieve(loginInfo).flatMap {
              case Some(user) => jwtAuthService.create(loginInfo)
                .flatMap { authenticator =>
                  silhouetteJwt.env.eventBus.publish(LoginEvent(user, request))
                  silhouetteJwt.env.authenticatorService.init(authenticator).map { token =>
                    Ok(Json.obj("token" -> token))
                  }
                }
              case None => Future.failed(new IdentityNotFoundException("Couldn't find user"))
            }
          }.recover {
          case e: ProviderException =>
            Unauthorized(Json.obj("message" -> "Invalid credentials"))
        }
      }
    )
  }


  def socialAuth(provider: String) = Action.async { implicit request =>
    (socialProviderRegistry.get[SocialProvider](provider) match {
      case Some(p: SocialProvider with CommonSocialProfileBuilder) => {
        p.authenticate().flatMap {
          case Left(result) => Future.successful(result)
          case Right(authInfo) => {
            p.retrieveProfile(authInfo).flatMap { profile => {
              loginInfoDAO.getAuthenticationProviders(profile.email.get).flatMap { providers =>
                if (providers.contains(provider) || providers.isEmpty) {
                  for {
                    user <- userService.saveOrUpdate(profile.firstName.getOrElse(""), profile.lastName.getOrElse(""), profile.email.getOrElse(""), profile.loginInfo)
                    _ <- authInfoRepository.add(profile.loginInfo, authInfo)
                    authenticator <- jwtAuthService.create(profile.loginInfo)
                    token <- jwtAuthService.init(authenticator)
                    result <- jwtAuthService.embed(token, Redirect(s"http://localhost:3000/oauth/google?name=${profile.fullName.getOrElse("")}&token=$token"))
                  } yield {
                    result
                  }
                } else {
                  Future.successful(BadRequest(Json.obj("message" -> "User is already exist (email)")))
                }
              }
            }
            }
          }
        }
      }
      case None => Future.successful(BadRequest(Json.obj("message" -> s"No '$provider' provider")))
    })
  }

  def me = silhouetteJwt.SecuredAction(errorHandler) { implicit request =>
    Ok(Json.toJson(request.identity))
  }

}

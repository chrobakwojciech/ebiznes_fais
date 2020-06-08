package controllers.api

import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.{LoginEvent, LoginInfo, Silhouette}
import com.mohiva.play.silhouette.api.services.{AuthenticatorResult, AuthenticatorService}
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.impl.authenticators.{CookieAuthenticator, JWTAuthenticator}
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.Inject
import models.User
import play.api.i18n.Messages
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{AbstractController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import repositories.UserRepository
import utils.{CookieEnv, JwtEnv}

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
                                            userRepository: UserRepository,
                                            silhouetteCookie: Silhouette[CookieEnv],
                                            silhouetteJwt: Silhouette[JwtEnv],
                                            credentialsProvider: CredentialsProvider)
                                           (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  val cookieAuthService: AuthenticatorService[CookieAuthenticator] = silhouetteCookie.env.authenticatorService
  val jwtAuthService: AuthenticatorService[JWTAuthenticator] = silhouetteJwt.env.authenticatorService

  def signUp() = silhouetteCookie.UnsecuredAction(parse.json).async { implicit request =>
    val body = request.body
    body.validate[SignUp].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      user => {
        userRepository.retrieve(LoginInfo(CredentialsProvider.ID, user.email))
          .flatMap((uo: Option[User]) =>
            uo.fold({
              userRepository.create2(user.firstName, user.lastName, user.email, user.password).flatMap(cookieAuthService.create(_))
                .flatMap(cookieAuthService.init(_))
                .flatMap(cookieAuthService.embed(_, Ok(Json.obj("message" -> "tutaj 1"))))
            })({ _ =>
              Future.successful(AuthenticatorResult(Ok(Json.obj("message" -> "Tutaj 2"))))
            })
          )
      }
    )
  }

  def signIn() = silhouetteCookie.UnsecuredAction(parse.json).async { implicit request =>
    val body = request.body
    body.validate[SignIn].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      credentials => {
        credentialsProvider.authenticate(credentials = Credentials(credentials.email, credentials.password))
          .flatMap { loginInfo =>
            cookieAuthService.create(loginInfo)
              .flatMap(cookieAuthService.init(_))
              .flatMap(cookieAuthService.embed(_, Ok(Json.obj("message" -> "tutaj 1", "test" -> loginInfo))))
          }.recover {
          case e: Exception =>
            BadRequest(Json.obj("message" -> e.getMessage))
        }
      }
    )
  }

  def jwt() = silhouetteJwt.UnsecuredAction(parse.json).async { implicit request =>
    val body = request.body
    body.validate[SignIn].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      credentials => {
        credentialsProvider.authenticate(credentials = Credentials(credentials.email, credentials.password))
          .flatMap { loginInfo =>
            userRepository.retrieve(loginInfo).flatMap {
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

  def me = silhouetteJwt.SecuredAction { implicit request =>
    Ok(Json.toJson(request.identity))
  }
//
//  def isAuthenticated = silhouetteCookie.UserAwareAction { implicit request =>
//    request.identity match {
//      case Some(identity) => Ok
//      case None => Unauthorized
//    }
//  }
}

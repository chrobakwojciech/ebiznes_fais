package controllers.api

import com.mohiva.play.silhouette.api.{LoginInfo, Silhouette}
import com.mohiva.play.silhouette.api.services.{AuthenticatorResult, AuthenticatorService}
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.Inject
import models.User
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{AbstractController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import repositories.UserRepository
import utils.DefaultEnv

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
                                            silhouette: Silhouette[DefaultEnv],
                                            credentialsProvider: CredentialsProvider)
                                           (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  val authService: AuthenticatorService[CookieAuthenticator] = silhouette.env.authenticatorService

  def signUp() = silhouette.UnsecuredAction(parse.json).async { implicit request =>
    val body = request.body
    body.validate[SignUp].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      user => {
        userRepository.retrieve(LoginInfo(CredentialsProvider.ID, user.email))
          .flatMap((uo: Option[User]) =>
            uo.fold({
              userRepository.create2(user.firstName, user.lastName, user.email, user.password).flatMap(authService.create(_))
                .flatMap(authService.init(_))
                .flatMap(authService.embed(_, Ok(Json.obj("message" -> "tutaj 1"))))
            })({ _ =>
              Future.successful(AuthenticatorResult(Ok(Json.obj("message" -> "Tutaj 2"))))
            })
          )
      }
    )
  }

  def signIn() = silhouette.UnsecuredAction(parse.json).async { implicit request =>
    val body = request.body
    body.validate[SignIn].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      credentials => {
        credentialsProvider.authenticate(credentials = Credentials(credentials.email, credentials.password))
          .flatMap { loginInfo =>
            authService.create(loginInfo)
              .flatMap(authService.init(_))
              .flatMap(authService.embed(_, Ok(Json.obj("message" -> "tutaj 1", "test" -> loginInfo))))
          }.recover {
          case e: Exception =>
            BadRequest(Json.obj("message" -> e.getMessage))
        }
      }
    )
  }

  def me = silhouette.SecuredAction { implicit request =>
    Ok(Json.toJson(request.identity))
  }

  def isAuthenticated = silhouette.UserAwareAction { implicit request =>
    request.identity match {
      case Some(identity) => Ok
      case None => Unauthorized
    }
  }
}

package controllers

import akka.event.EventBus
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.services.{AuthenticatorResult, AuthenticatorService}
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.api.{LoginEvent, LoginInfo, LogoutEvent, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.{CookieAuthenticator, JWTAuthenticator}
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.{Inject, Singleton}
import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}
import repositories.UserRepository
import utils.auth.{CookieEnv, DashboardErrorHandler, JwtEnv}

import scala.concurrent.{ExecutionContext, Future}

case class SignUp(firstName: String,
                  lastName: String,
                  email: String,
                  password: String
                 )


case class SignIn(email: String,
                  password: String
                 )


@Singleton
class AuthenticationController @Inject()(cc: MessagesControllerComponents,
                                            userRepository: UserRepository,
                                            errorHandler: DashboardErrorHandler,
                                            silhouetteCookie: Silhouette[CookieEnv],
                                            credentialsProvider: CredentialsProvider)
                                           (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  val cookieAuthService: AuthenticatorService[CookieAuthenticator] = silhouetteCookie.env.authenticatorService
  val cookieEventBus = silhouetteCookie.env.eventBus

  val signUpForm: Form[SignUp] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText
    )(SignUp.apply)(SignUp.unapply)
  }

  val signInForm: Form[SignIn] = Form {
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(SignIn.apply)(SignIn.unapply)
  }


  def signUp = Action { implicit request =>
    Ok(views.html.auth.signup(signUpForm))
  }

  def signIn = Action { implicit request =>
    Ok(views.html.auth.signin(signInForm))
  }

  def signUpHandler = Action.async { implicit request =>
    val errorFunction = { formWithErrors: Form[SignUp] =>
      Future {
        Redirect(routes.AuthenticationController.signUp()).flashing("error" -> "Błąd podczas rejestracji!")
      }
    }

    val successFunction = { user: SignUp =>
      userRepository.retrieve(LoginInfo(CredentialsProvider.ID, user.email))
        .flatMap((uo: Option[User]) =>
          uo.fold({
            userRepository.create(user.firstName, user.lastName, user.email, user.password).flatMap(cookieAuthService.create(_))
              .flatMap(cookieAuthService.init(_))
              .flatMap(cookieAuthService.embed(_, Redirect(routes.HomeController.index()).flashing("success" -> "Witamy!")))
          })({ _ =>
            Future.successful(AuthenticatorResult(Redirect(routes.AuthenticationController.signUp()).flashing("error" -> "Bład podczas rejestracji")))
          })
        )
    }
    signUpForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def signInHandler = Action.async { implicit request =>
    val errorFunction = { formWithErrors: Form[SignIn] =>
      Future {
        Redirect(routes.AuthenticationController.signIn()).flashing("error" -> "Błąd podczas logowania!")
      }
    }

    val successFunction = { user: SignIn =>
      credentialsProvider.authenticate(credentials = Credentials(user.email, user.password))
        .flatMap { loginInfo =>
          cookieAuthService.create(loginInfo)
            .flatMap(cookieAuthService.init(_))
            .flatMap(cookieAuthService.embed(_, Redirect(routes.HomeController.index()).flashing("success" -> "Zalogowano poprawnie")))
        }.recover {
        case e: Exception =>
          Redirect(routes.AuthenticationController.signIn()).flashing("error" -> e.getMessage)
      }
    }
    signInForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def signOut = silhouetteCookie.SecuredAction(errorHandler).async { implicit request =>
    cookieEventBus.publish(LogoutEvent(request.identity, request))
    cookieAuthService.discard(request.authenticator, Redirect(routes.AuthenticationController.signIn()).flashing("info" -> "Wylogowano!"))
  }
}


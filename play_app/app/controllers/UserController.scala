package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.{User, UserRoles}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.UserRepository
import utils.auth.{CookieEnv, RoleCookieAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class UserController @Inject()(userRepository: UserRepository, cc: MessagesControllerComponents,
                               silhouette: Silhouette[CookieEnv])(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
    val createUserForm: Form[CreateUserForm] = Form {
      mapping(
        "firstName" -> nonEmptyText,
        "lastName" -> nonEmptyText,
        "isAdmin" -> boolean
      )(CreateUserForm.apply)(CreateUserForm.unapply)
    }

  def getAll: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_] =>
    val users = userRepository.getAll();
    users.map(user => Ok(views.html.user.users(user)))
  }

  def delete(userId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_] =>
    userRepository.delete(userId).map(_ => Redirect(routes.UserController.getAll()).flashing("info" -> "Użytkownik usunięty!"))
  }

  def update(userId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_] =>
    val user: User = Await.result(userRepository.getById(userId), Duration.Inf).get
    val isAdmin: Boolean = user.role == UserRoles.Admin
    val updateForm = createUserForm.fill(CreateUserForm(user.firstName, user.lastName, isAdmin))
    Ok(views.html.user.update_user(userId, updateForm, isAdmin))
  }

  def updateUserHandler(userId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_] =>
    val errorFunction = { formWithErrors: Form[CreateUserForm] =>
      Future.successful(Redirect(routes.UserController.update(userId)).flashing("error" -> "Błąd podczas edycji użytkownika!"))
    }

    val successFunction = { user: CreateUserForm =>
      val userEmail: String = Await.result(userRepository.getById(userId), Duration.Inf).get.email
      var userRole: UserRoles.UserRole = UserRoles.User
      if (user.isAdmin) {
        userRole = UserRoles.Admin
      }
      userRepository.update(userId, user.firstName, user.lastName, userEmail, userRole).map { _ =>
        Redirect(routes.UserController.getAll()).flashing("success" -> "Użytkownik zmodyfikowany!")
      };
    }
    createUserForm.bindFromRequest.fold(errorFunction, successFunction)
  }

}

case class CreateUserForm(firstName: String,
                          lastName: String,
                          isAdmin: Boolean
                         )

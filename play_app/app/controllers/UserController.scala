package controllers

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.UserRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(userRepository: UserRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createUserForm: Form[CreateUserForm] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val users = userRepository.getAll();
    users.map(user => Ok(views.html.user.users(user)))
  }

  def get(userId: String): Action[AnyContent] = Action.async { implicit request =>
    userRepository.getById(userId) map {
      case Some(u) => Ok(views.html.user.user(u))
      case None => Redirect(routes.UserController.getAll())
    }
  }

  def create: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.user.add_user(createUserForm))
  }

  def createUserHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateUserForm] =>
      Future {
        Redirect(routes.UserController.create()).flashing("error" -> "Błąd podczas dodawania użytkownika!")
      }
    }

    val successFunction = { user: CreateUserForm =>
      userRepository.create(user.firstName, user.lastName, user.email, user.password).map { _ =>
        Redirect(routes.UserController.getAll()).flashing("success" -> "Użytkownik dodany!")
      };
    }
    createUserForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(userId: String): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    userRepository.delete(userId).map(_ => Redirect(routes.UserController.getAll()).flashing("info" -> "Użytkownik usunięty!"))
  }

}

case class CreateUserForm(firstName: String,
                          lastName: String,
                          email: String,
                          password: String
                         )

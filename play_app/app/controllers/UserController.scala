package controllers

import javax.inject.{Inject, Singleton}
import models.User
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
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val users = userRepository.getAll();
    users.map(user => Ok(views.html.user.users(user)))
  }

  def get(userId: String): Action[AnyContent] = Action {
    Ok("")
  }

  def create: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.user.add_user(createUserForm))
  }


  def createUserHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateUserForm] =>
      val users = userRepository.getAll();
      users.map(user => BadRequest(views.html.user.users(user)))
    }

    val successFunction = { user: CreateUserForm =>
      userRepository.create(user.firstName, user.lastName, user.email, user.password).map {_ =>
        Redirect(routes.UserController.create()).flashing("success" -> "user.created")};
    }
    createUserForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def update(userId: String): Action[AnyContent] = Action {
    Ok("")
  }

  def delete(userId: String): Action[AnyContent] = Action {
    Ok("")
  }
}

case class CreateUserForm(firstName: String,
                          lastName: String,
                          email: String,
                          password: String
                         )

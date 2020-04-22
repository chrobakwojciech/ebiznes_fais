package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import repositories.UserRepository

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(userRepository: UserRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val users = userRepository.getAll();
    users.map(user => Ok(views.html.user.users(user)))
  }

  def get(userId: String) = Action {
    Ok("")
  }

  def create = Action {
    Ok("")
  }

  def update(userId: String) = Action {
    Ok("")
  }

  def delete(userId: String) = Action {
    Ok("")
  }


}

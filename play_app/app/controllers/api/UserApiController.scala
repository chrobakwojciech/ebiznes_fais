//package controllers.api
//
//import javax.inject.{Inject, Singleton}
//import play.api.libs.json.{JsError, Json}
//import play.api.mvc._
//import repositories.UserRepository
//
//import scala.concurrent.ExecutionContext
//
//case class CreateUser(firstName: String,
//                      lastName: String,
//                      email: String,
//                      password: String
//                     )
//
//object CreateUser {
//  implicit val userFormat = Json.format[CreateUser]
//}
//
//
//case class UpdateUser(firstName: Option[String],
//                      lastName: Option[String],
//                      email: Option[String],
//                      password: Option[String]
//                     )
//
//object UpdateUser {
//  implicit val userFormat = Json.format[UpdateUser]
//}
//
//
//@Singleton
//class UserApiController @Inject()(userRepository: UserRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
//
//  def getAll: Action[AnyContent] = Action.async { implicit request =>
//    val users = userRepository.getAll();
//    users.map(user => Ok(Json.toJson(user)))
//  }
//
//  def get(id: String) = Action.async { implicit request =>
//    userRepository.getById(id) map {
//      case Some(u) => Ok(Json.toJson(u))
//      case None => NotFound(Json.obj("message" -> "User does not exist"))
//    }
//  }
//
//  def create() = Action(parse.json) { implicit request =>
//    val body = request.body
//    body.validate[CreateUser].fold(
//      errors => {
//        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
//      },
//      user => {
//        userRepository.create(user.firstName, user.lastName, user.email, user.password)
//        Ok(Json.obj("message" -> "User created"))
//      }
//    )
//  }
//
//  def update(id: String) = Action.async(parse.json) { implicit request =>
//    userRepository.getById(id) map {
//      case Some(u) => {
//        val body = request.body
//        body.validate[UpdateUser].fold(
//          errors => {
//            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
//          },
//          user => {
//            userRepository.update(id, user.firstName.getOrElse(u.firstName), user.lastName.getOrElse(u.lastName), user.email.getOrElse(u.email), user.password.getOrElse(u.password))
//            Ok(Json.obj("message" -> "User updated"))
//          }
//        )
//      }
//      case None => NotFound(Json.obj("message" -> "User does not exist"))
//    }
//  }
//
//  def delete(id: String) = Action.async { implicit request =>
//    userRepository.getById(id) map {
//      case Some(u) => {
//        userRepository.delete(id)
//        Ok(Json.obj("message" -> "User deleted"))
//      }
//      case None => NotFound(Json.obj("message" -> "User does not exist"))
//    }
//  }
//
//}

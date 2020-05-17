package controllers.api

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{ActorRepository, DirectorRepository}

import scala.concurrent.ExecutionContext

case class CreateDirector(firstName: String,
                      lastName: String,
                      img: String
                     )

object CreateDirector {
  implicit val userFormat = Json.format[CreateDirector]
}


case class UpdateDirector(firstName: Option[String],
                      lastName: Option[String],
                      img: Option[String]
                     )

object UpdateDirector {
  implicit val userFormat = Json.format[UpdateDirector]
}


@Singleton
class DirectorApiController @Inject()(directorRepository: DirectorRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val directors = directorRepository.getAll()
    directors.map(director => Ok(Json.toJson(director)))
  }

  def get(id: String) = Action.async { implicit request =>
    directorRepository.getById(id) map {
      case Some(d) => Ok(Json.toJson(d))
      case None => NotFound(Json.obj("message" -> "Director does not exist"))
    }
  }

  def create() = Action(parse.json) { implicit request =>
    val body = request.body
    body.validate[CreateDirector].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      director => {
        directorRepository.create(director.firstName, director.lastName, director.img)
        Ok(Json.obj("message" -> "Director created"))
      }
    )
  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
    directorRepository.getById(id) map {
      case Some(d) => {
        val body = request.body
        body.validate[UpdateDirector].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          director => {
            directorRepository.update(id, director.firstName.getOrElse(d.firstName), director.lastName.getOrElse(d.lastName), director.img.getOrElse(d.img))
            Ok(Json.obj("message" -> "Director updated"))
          }
        )
      }
      case None => NotFound(Json.obj("message" -> "Director does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    directorRepository.getById(id) map {
      case Some(d) => {
        directorRepository.delete(id)
        Ok(Json.obj("message" -> "Director deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Director does not exist"))
    }
  }

}

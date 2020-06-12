package controllers.api

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{ActorRepository, MovieRepository}

import scala.concurrent.ExecutionContext

case class CreateActor(firstName: String,
                      lastName: String,
                      img: String
                     )

object CreateActor {
  implicit val actorFormat = Json.format[CreateActor]
}


case class UpdateActor(firstName: Option[String],
                      lastName: Option[String],
                      img: Option[String]
                     )

object UpdateActor {
  implicit val actorFormat = Json.format[UpdateActor]
}


@Singleton
class ActorApiController @Inject()(actorRepository: ActorRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val actors = actorRepository.getAll();
    actors.map(actor => Ok(Json.toJson(actor)))
  }

  def get(id: String) = Action.async { implicit request =>
    actorRepository.getById(id) map {
      case Some(a) => Ok(Json.toJson(a))
      case None => NotFound(Json.obj("message" -> "Actor does not exist"))
    }
  }

  def getMovies(id: String) = Action.async { implicit request =>
    val movies = movieRepository.getForActor(id)
    movies.map(movie => Ok(Json.toJson(movie)))
  }

  def create() = Action(parse.json) { implicit request =>
    val body = request.body
    body.validate[CreateActor].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      actor => {
        actorRepository.create(actor.firstName, actor.lastName, actor.img)
        Ok(Json.obj("message" -> "Actor created"))
      }
    )
  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
    actorRepository.getById(id) map {
      case Some(u) => {
        val body = request.body
        body.validate[UpdateActor].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          actor => {
            actorRepository.update(id, actor.firstName.getOrElse(u.firstName), actor.lastName.getOrElse(u.lastName), actor.img.getOrElse(u.img))
            Ok(Json.obj("message" -> "Actor updated"))
          }
        )
      }
      case None => NotFound(Json.obj("message" -> "Actor does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    actorRepository.getById(id) map {
      case Some(u) => {
        actorRepository.delete(id)
        Ok(Json.obj("message" -> "Actor deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Actor does not exist"))
    }
  }

}

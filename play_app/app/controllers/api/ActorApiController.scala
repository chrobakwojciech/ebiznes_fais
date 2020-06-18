package controllers.api

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.UserRoles
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{ActorRepository, MovieRepository}
import utils.auth.{JsonErrorHandler, JwtEnv, RoleJWTAuthorization}

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
class ActorApiController @Inject()(
                                    actorRepository: ActorRepository,
                                    movieRepository: MovieRepository,
                                    errorHandler: JsonErrorHandler,
                                    silhouette: Silhouette[JwtEnv],
                                    cc: MessagesControllerComponents
                                  )(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val actorNotFound = NotFound(Json.obj("message" -> "Actor does not exist"));

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val actors = actorRepository.getAll();
    actors.map(actor => Ok(Json.toJson(actor)))
  }

  def get(id: String) = Action.async { implicit request =>
    actorRepository.getById(id) map {
      case Some(a) => Ok(Json.toJson(a))
      case None => actorNotFound
    }
  }

  def getMovies(id: String) = Action.async { implicit request =>
    val movies = movieRepository.getForActor(id)
    movies.map(movie => Ok(Json.toJson(movie)))
  }

  def create() = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)) { implicit request =>
    val body = request.body.asJson.get
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

  def update(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async { implicit request =>
    actorRepository.getById(id) map {
      case Some(u) => {
        val body = request.body.asJson.get
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
      case None => actorNotFound
    }
  }

  def delete(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async { implicit request =>
    actorRepository.getById(id) map {
      case Some(u) => {
        actorRepository.delete(id)
        Ok(Json.obj("message" -> "Actor deleted"))
      }
      case None => actorNotFound
    }
  }

}

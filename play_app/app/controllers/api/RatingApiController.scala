package controllers.api

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.{Movie, Rating, User, UserRoles}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{MovieRepository, RatingRepository, UserRepository}
import utils.auth.{JsonErrorHandler, JwtEnv, RoleJWTAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

case class CreateRating(value: Int,
                         movie: String)

object CreateRating {
  implicit val ratingFormat = Json.format[CreateRating]
}


@Singleton
class RatingApiController @Inject()(ratingRepository: RatingRepository, userRepository: UserRepository, movieRepository: MovieRepository,
                                    errorHandler: JsonErrorHandler,
                                    silhouette: Silhouette[JwtEnv], cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val ratings: Future[Seq[(Rating, Movie, User)]] = ratingRepository.getAllWithMovieAndUser()
    ratings.map(rating => {
      val newRating = rating.map {
        case (r, m, u) => Json.obj("id" -> r.id, "value" -> r.value, "movie" -> m, "user" -> u)
      }
      Ok(Json.toJson(newRating))
    })
  }

  def get(id: String) = Action.async { implicit request =>
    ratingRepository.getByIdWithMovieAndUser(id) map {
      case Some(r) => Ok(Json.toJson(Json.obj("id" -> r._1.id, "value" -> r._1.value, "movie" -> r._2, "user" -> r._3)))
      case None => NotFound(Json.obj("message" -> "Rating does not exist"))
    }
  }

  def create() = silhouette.SecuredAction(errorHandler).async(parse.json) { implicit request =>
    val body = request.body
    body.validate[CreateRating].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      rating => {
        // @FIXME
        val userExist: Boolean = Await.result(userRepository.isExist(request.identity.id), Duration.Inf)
        if (!userExist) {
          Future.successful(BadRequest(Json.obj("message" -> "User does not exist")))
        } else {
          val movieExist: Boolean = Await.result(movieRepository.isExist(rating.movie), Duration.Inf)
          if (!movieExist) {
            Future.successful(BadRequest(Json.obj("message" -> "Movie does not exist")))
          } else {
            ratingRepository.create(rating.value, request.identity.id, rating.movie)
            Future.successful(Ok(Json.obj("message" -> "Rating created")))
          }
        }
      }
    )
  }

  def delete(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async { implicit request =>
    ratingRepository.getById(id) map {
      case Some(r) => {
        ratingRepository.delete(id)
        Ok(Json.obj("message" -> "Rating deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Rating does not exist"))
    }
  }

}

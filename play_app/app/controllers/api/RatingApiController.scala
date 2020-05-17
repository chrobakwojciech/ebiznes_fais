package controllers.api

import javax.inject.{Inject, Singleton}
import models.{Comment, Movie, Rating, User}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{CommentRepository, RatingRepository}

import scala.concurrent.{ExecutionContext, Future}

case class CreateRating(value: Int,
                         user: String,
                         movie: String)

object CreateRating {
  implicit val ratingFormat = Json.format[CreateRating]
}


case class UpdateRating(value: Option[Int],
                         user: Option[String],
                         movie: Option[String])

object UpdateRating {
  implicit val ratingFormat = Json.format[UpdateRating]
}


@Singleton
class RatingApiController @Inject()(ratingRepository: RatingRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

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

  def create() = Action(parse.json) { implicit request =>
    val body = request.body
    body.validate[CreateRating].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      rating => {
        ratingRepository.create(rating.value, rating.user, rating.movie)
        Ok(Json.obj("message" -> "Rating created"))
      }
    )
  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
    ratingRepository.getById(id) map {
      case Some(r) => {
        val body = request.body
        body.validate[UpdateRating].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          rating => {
            ratingRepository.update(id, rating.value.getOrElse(r.value), rating.user.getOrElse(r.user), rating.movie.getOrElse(r.movie))
            Ok(Json.obj("message" -> "Rating updated"))
          }
        )
      }
      case None => NotFound(Json.obj("message" -> "Rating does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    ratingRepository.getById(id) map {
      case Some(r) => {
        ratingRepository.delete(id)
        Ok(Json.obj("message" -> "Rating deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Rating does not exist"))
    }
  }

}

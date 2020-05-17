package controllers.api

import akka.actor.Status.Success
import akka.event.Logging.Error
import javax.inject.{Inject, Singleton}
import models.Actor
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc._
import repositories.{ActorRepository, DirectorRepository, GenreRepository, MovieRepository, PaymentRepository}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

case class CreateMovie(title: String,
                       description: String,
                       productionYear: String,
                       price: Double,
                       img: String,
                       actors: Seq[String],
                       directors: Seq[String],
                       genres: Seq[String])

object CreateMovie {
  implicit val movieFormat = Json.format[CreateMovie]
}


case class UpdateMovie(title: Option[String],
                       description: Option[String],
                       productionYear: Option[String],
                       price: Option[Double],
                       img: Option[String],
                       actors: Option[Seq[String]],
                       directors: Option[Seq[String]],
                       genres: Option[Seq[String]])

object UpdateMovie {
  implicit val movieFormat = Json.format[UpdateMovie]
}


@Singleton
class MovieApiController @Inject()(movieRepository: MovieRepository, actorRepository: ActorRepository, directorRepository: DirectorRepository, genreRepository: GenreRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val movies = movieRepository.getAll()
    movies.map(movie => Ok(Json.toJson(movie)))
  }

  def get(id: String) = Action.async { implicit request =>
    movieRepository.getById(id) map {
      case Some(m) => Ok(Json.toJson(m))
      case None => NotFound(Json.obj("message" -> "Movie does not exist"))
    }
  }

  def create() = Action(parse.json) { request =>
    val body = request.body

    body.validate[CreateMovie].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      movie => {
        var invalidActors: Seq[String] = Seq()
        for (a <- movie.actors) {
          if (!(Await.result(actorRepository.isExist(a), Duration.Inf))) {
            invalidActors = invalidActors :+ a
          }
        }

        var invalidDirectors: Seq[String] = Seq()
        for (d <- movie.directors) {
          if (!(Await.result(directorRepository.isExist(d), Duration.Inf))) {
            invalidDirectors = invalidDirectors :+ d
          }
        }

        var invalidGenres: Seq[String] = Seq()
        for (g <- movie.genres) {
          if (!(Await.result(genreRepository.isExist(g), Duration.Inf))) {
            invalidGenres = invalidGenres :+ g
          }
        }
        val valid: Boolean = invalidActors.isEmpty && invalidDirectors.isEmpty && invalidGenres.isEmpty
        if (!valid) {
          BadRequest(Json.obj("invalid_actors" -> invalidActors, "invalid_directors" -> invalidDirectors, "invalidGenres" -> invalidGenres))
        } else {
          movieRepository.create(movie.title, movie.description, movie.productionYear, movie.price, movie.img, movie.actors, movie.directors, movie.genres)
          Ok(Json.obj("message" -> "Movie created"))
        }
      }
    )
  }


//
//  def update(id: String) = Action.async(parse.json) { implicit request =>
//    paymentRepository.getById(id) map {
//      case Some(p) => {
//        val body = request.body
//        body.validate[UpdatePayment].fold(
//          errors => {
//            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
//          },
//          payment => {
//            paymentRepository.update(id, payment.name.getOrElse(p.name))
//            Ok(Json.obj("message" -> "Payment updated"))
//          }
//        )
//      }
//      case None => NotFound(Json.obj("message" -> "Payment does not exist"))
//    }
//  }
//
//  def delete(id: String) = Action.async { implicit request =>
//    paymentRepository.getById(id) map {
//      case Some(g) => {
//        paymentRepository.delete(id)
//        Ok(Json.obj("message" -> "Payment deleted"))
//      }
//      case None => NotFound(Json.obj("message" -> "Payment does not exist"))
//    }
//  }

}

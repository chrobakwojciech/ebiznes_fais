package controllers.api

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.{Movie, Rating}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories._
import utils.{CookieEnv, JwtEnv}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

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
class MovieApiController @Inject()(movieRepository: MovieRepository,
                                   actorRepository: ActorRepository,
                                   directorRepository: DirectorRepository,
                                   genreRepository: GenreRepository,
                                   commentRepository: CommentRepository,
                                   ratingRepository: RatingRepository,
                                   cc: MessagesControllerComponents,
                                   silhouette: Silhouette[JwtEnv]
                                  )(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll = silhouette.SecuredAction.async { implicit request =>
    val movies = movieRepository.getAll()
    movies.map(movie => Ok(Json.toJson(movie)))
  }

  def get(id: String) = Action.async { implicit request =>
    movieRepository.getById(id) map {
      case Some(m) => Ok(Json.toJson(m))
      case None => NotFound(Json.obj("message" -> "Movie does not exist"))
    }
  }

  def getActors(id: String) = Action.async { implicit request =>
    val actors = actorRepository.getForMovie(id)
    actors.map(actor => Ok(Json.toJson(actor)))
  }

  def getDirectors(id: String) = Action.async { implicit request =>
    val directors = directorRepository.getForMovie(id)
    directors.map(director => Ok(Json.toJson(director)))
  }

  def getGenres(id: String) = Action.async { implicit request =>
    val genres = genreRepository.getForMovie(id)
    genres.map(genre => Ok(Json.toJson(genre)))
  }

  def getComments(id: String) = Action.async { implicit request =>
    val comments = commentRepository.getForMovie(id)
    comments.map(comment => {
      val newComment = comment.map {
        case (c, u) => Json.obj("id" -> c.id, "content" -> c.content, "user" -> u)
      }
      Ok(Json.toJson(newComment))
    })
  }

  def getRatings(id: String) = Action.async { implicit request =>
    val ratings = ratingRepository.getForMovie(id)
    ratings.map(rating => {
      val newRating = rating.map {
        case (r, u) => Json.obj("id" -> r.id, "value" -> r.value, "user" -> u)
      }
      Ok(Json.toJson(newRating))
    })
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

  def update(id: String) = Action.async(parse.json) { implicit request =>
    movieRepository.getById(id) map {
      case Some(m) => {
        val body = request.body
        body.validate[UpdateMovie].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          movie => {
            var invalidActors: Seq[String] = Seq()
            if (movie.actors.nonEmpty) {
              for (a <- movie.actors.get) {
                if (!(Await.result(actorRepository.isExist(a), Duration.Inf))) {
                  invalidActors = invalidActors :+ a
                }
              }
            }


            var invalidDirectors: Seq[String] = Seq()
            if (movie.directors.nonEmpty) {
              for (d <- movie.directors.get) {
                if (!(Await.result(directorRepository.isExist(d), Duration.Inf))) {
                  invalidDirectors = invalidDirectors :+ d
                }
              }
            }

            var invalidGenres: Seq[String] = Seq()
            if (movie.genres.nonEmpty) {
              for (g <- movie.genres.get) {
                if (!(Await.result(genreRepository.isExist(g), Duration.Inf))) {
                  invalidGenres = invalidGenres :+ g
                }
              }
            }

            val valid: Boolean = invalidActors.isEmpty && invalidDirectors.isEmpty && invalidGenres.isEmpty
            if (!valid) {
              BadRequest(Json.obj("invalid_actors" -> invalidActors, "invalid_directors" -> invalidDirectors, "invalidGenres" -> invalidGenres))
            } else {
              val currentActors: Seq[String] = Await.result(actorRepository.getForMovie(id), Duration.Inf).map(_.id)
              val currentDirectors: Seq[String] = Await.result(directorRepository.getForMovie(id), Duration.Inf).map(_.id)
              val currentGenres: Seq[String] = Await.result(genreRepository.getForMovie(id), Duration.Inf).map(_.id)

              movieRepository.update(id,
                movie.title.getOrElse(m.title),
                movie.description.getOrElse(m.description),
                movie.productionYear.getOrElse(m.productionYear),
                movie.price.getOrElse(m.price),
                movie.img.getOrElse(m.img),
                movie.actors.getOrElse(currentActors),
                movie.directors.getOrElse(currentDirectors),
                movie.genres.getOrElse(currentGenres)
              )
              Ok(Json.obj("message" -> "Movie updated"))
            }
          }
        )
      }
      case None => NotFound(Json.obj("message" -> "Movie does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    movieRepository.getById(id) map {
      case Some(m) => {
        movieRepository.delete(id)
        Ok(Json.obj("message" -> "Movie deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Movie does not exist"))
    }
  }

}

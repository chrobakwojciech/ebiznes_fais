package controllers.api

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{GenreRepository, MovieRepository}

import scala.concurrent.ExecutionContext

case class CreateGenre(name: String)

object CreateGenre {
  implicit val genreFormat = Json.format[CreateGenre]
}


case class UpdateGenre(name: Option[String])

object UpdateGenre {
  implicit val genreFormat = Json.format[UpdateGenre]
}


@Singleton
class GenreApiController @Inject()(genreRepository: GenreRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val genres = genreRepository.getAll()
    genres.map(genre => Ok(Json.toJson(genre)))
  }

  def getMovies(id: String): Action[AnyContent] = Action.async { implicit request =>
    val movies = movieRepository.getForGenre(id)
    movies.map(movie => Ok(Json.toJson(movie)))
  }

  def get(id: String) = Action.async { implicit request =>
    genreRepository.getById(id) map {
      case Some(g) => Ok(Json.toJson(g))
      case None => NotFound(Json.obj("message" -> "Genre does not exist"))
    }
  }

  def create() = Action(parse.json) { implicit request =>
    val body = request.body
    body.validate[CreateGenre].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      genre => {
        genreRepository.create(genre.name)
        Ok(Json.obj("message" -> "Genre created"))
      }
    )
  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
    genreRepository.getById(id) map {
      case Some(g) => {
        val body = request.body
        body.validate[UpdateGenre].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          genre => {
            genreRepository.update(id, genre.name.getOrElse(g.name))
            Ok(Json.obj("message" -> "Genre updated"))
          }
        )
      }
      case None => NotFound(Json.obj("message" -> "Genre does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    genreRepository.getById(id) map {
      case Some(g) => {
        genreRepository.delete(id)
        Ok(Json.obj("message" -> "Genre deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Genre does not exist"))
    }
  }

}

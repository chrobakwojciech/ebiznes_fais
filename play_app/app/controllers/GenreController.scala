package controllers

import javax.inject.{Inject, Singleton}
import models.Movie
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repositories.{GenreRepository, MovieRepository}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class GenreController @Inject()(genreRepository: GenreRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll = Action.async { implicit request =>
    val genres = genreRepository.getAll()
    genres.map(genre => Ok(views.html.genre.genres(genre)))
  }

  def get(genreId: String) = Action.async { implicit request =>
    var movies: Seq[Movie] = Seq[Movie]()
    movieRepository.getForGenre(genreId).onComplete {
      case Success(m) => movies = m
      case Failure(_) => print("fail")
    }

    genreRepository.getById(genreId) map {
      case Some(g) => Ok(views.html.genre.genre(g, movies))
      case None => Redirect(routes.GenreController.getAll())
    }
  }

  def create = Action {
    Ok("")
  }

  def update(genreId: String) = Action {
    Ok("")
  }

  def delete(genreId: String) = Action {
    Ok("")
  }


}

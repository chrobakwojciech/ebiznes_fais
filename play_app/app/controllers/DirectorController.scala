package controllers

import javax.inject.{Inject, Singleton}
import models.Movie
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repositories.{DirectorRepository, MovieRepository}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class DirectorController @Inject()(directorRepository: DirectorRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll = Action.async { implicit request =>
    val directors = directorRepository.getAll()
    directors.map(director => Ok(views.html.director.directors(director)))
  }

  def get(directorId: String) = Action.async { implicit request =>
    var movies: Seq[Movie] = Seq[Movie]()
    movieRepository.getForDirector(directorId).onComplete {
      case Success(m) => movies = m
      case Failure(_) => print("fail")
    }

    directorRepository.getById(directorId) map {
      case Some(d) => Ok(views.html.director.director(d, movies))
      case None => Redirect(routes.DirectorController.getAll())
    }
  }

  def create = Action {
    Ok("")
  }

  def update(directorId: String) = Action {
    Ok("")
  }

  def delete(directorId: String) = Action {
    Ok("")
  }


}

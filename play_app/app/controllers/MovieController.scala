package controllers

import javax.inject.{Inject, Singleton}
import models._
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repositories._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class MovieController @Inject()(movieRepository: MovieRepository, directorRepository: DirectorRepository, actorRepository: ActorRepository, genreRepository: GenreRepository, ratingRepository: RatingRepository, commentRepository: CommentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll = Action.async { implicit request =>
    val movies = movieRepository.getAll()
    movies.map(movie => Ok(views.html.movie.movies(movie)))
  }

  def get(movieId: String) = Action.async { implicit request =>
    var comments: Seq[(Comment, User)] = Seq[(Comment, User)]()
    commentRepository.getForMovie(movieId).onComplete {
      case Success(c) => comments = c
      case Failure(_) => print("fail")
    }

    var ratings: Seq[(Rating, User)] = Seq[(Rating, User)]()
    ratingRepository.getForMovie(movieId).onComplete {
      case Success(r) => ratings = r
      case Failure(_) => print("fail")
    }

    var actors: Seq[Actor] = Seq[Actor]()
    actorRepository.getForMovie(movieId).onComplete {
      case Success(a) => actors = a
      case Failure(_) => print("fail")
    }

    var genres: Seq[Genre] = Seq[Genre]()
    genreRepository.getForMovie(movieId).onComplete {
      case Success(g) => genres = g
      case Failure(_) => print("fail")
    }

    var directors: Seq[Director] = Seq[Director]()
    directorRepository.getForMovie(movieId).onComplete {
      case Success(d) => directors = d
      case Failure(_) => print("fail")
    }

    movieRepository.getById(movieId) map {
      case Some(m) => Ok(views.html.movie.movie(m, comments, ratings, actors, directors, genres))
      case None => Redirect(routes.MovieController.getAll())
    }
  }

  def create = Action {
    Ok("")
  }

  def update(movieId: String) = Action {
    Ok("")
  }

  def delete(movieId: String) = Action {
    Ok("")
  }


}

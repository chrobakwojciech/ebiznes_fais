package controllers

import javax.inject.{Inject, Singleton}
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}
import repositories._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class MovieController @Inject()(movieRepository: MovieRepository, directorRepository: DirectorRepository, actorRepository: ActorRepository, genreRepository: GenreRepository, ratingRepository: RatingRepository, commentRepository: CommentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createMovieForm: Form[CreateMovieForm] = Form {
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "productionYear" -> nonEmptyText,
      "price" -> of(doubleFormat),
      "img" -> nonEmptyText
    )(CreateMovieForm.apply)(CreateMovieForm.unapply)
  }

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

  def create: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.movie.add_movie(createMovieForm))
  }

  def update(movieId: String) = Action {
    Ok("")
  }

  def delete(movieId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    movieRepository.delete(movieId).map(_ => Redirect(routes.MovieController.getAll()).flashing("info" -> "Film usunięty!"))
  }

  def createMovieHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateMovieForm] =>
      Future {
        Redirect(routes.MovieController.create()).flashing("error" -> "Błąd podczas dodawania filmu!")
      }
    }

    val successFunction = { movie: CreateMovieForm =>
      movieRepository.create(movie.title, movie.description, movie.productionYear, movie.price, movie.img).map { _ =>
        Redirect(routes.MovieController.getAll()).flashing("success" -> "Film dodany!")
      }
    }
    createMovieForm.bindFromRequest.fold(errorFunction, successFunction)
  }
}

case class CreateMovieForm(title: String,
                           description: String,
                           productionYear: String,
                           price: Double,
                           img: String
                          )

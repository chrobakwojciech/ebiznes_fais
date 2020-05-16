package controllers

import javax.inject.{Inject, Singleton}
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc._
import repositories._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class MovieController @Inject()(movieRepository: MovieRepository, directorRepository: DirectorRepository, actorRepository: ActorRepository, genreRepository: GenreRepository, ratingRepository: RatingRepository, commentRepository: CommentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createMovieForm: Form[CreateMovieForm] = Form {
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "productionYear" -> nonEmptyText,
      "price" -> of(doubleFormat),
      "img" -> nonEmptyText,
      "actors" -> seq(nonEmptyText),
      "directors" -> seq(nonEmptyText),
      "genres" -> seq(nonEmptyText)
    )(CreateMovieForm.apply)(CreateMovieForm.unapply)
  }

  def getAll = Action.async { implicit request =>
    val movies = movieRepository.getAll()
    movies.map(movie => Ok(views.html.movie.movies(movie)))
  }

  def get(movieId: String) = Action.async { implicit request =>
    val comments: Seq[(Comment, User)] = Await.result(commentRepository.getForMovie(movieId), Duration.Inf)
    val ratings: Seq[(Rating, User)] = Await.result(ratingRepository.getForMovie(movieId), Duration.Inf)
    val actors: Seq[Actor] = Await.result(actorRepository.getForMovie(movieId), Duration.Inf)
    val genres: Seq[Genre] = Await.result(genreRepository.getForMovie(movieId), Duration.Inf)
    val directors: Seq[Director] = Await.result(directorRepository.getForMovie(movieId), Duration.Inf)

    movieRepository.getById(movieId) map {
      case Some(m) => Ok(views.html.movie.movie(m, comments, ratings, actors, directors, genres))
      case None => Redirect(routes.MovieController.getAll())
    }
  }

  def create: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val actors: Seq[Actor] = Await.result(actorRepository.getAll(), Duration.Inf)
    val directors: Seq[Director] = Await.result(directorRepository.getAll(), Duration.Inf)
    val genres: Seq[Genre] = Await.result(genreRepository.getAll(), Duration.Inf)
    Ok(views.html.movie.add_movie(createMovieForm, actors, directors, genres))
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
      movieRepository.create(movie.title, movie.description, movie.productionYear, movie.price, movie.img, movie.actors, movie.directors, movie.genres).map { _ =>
        Redirect(routes.MovieController.getAll()).flashing("success" -> "Film dodany!")
      }
    }
    createMovieForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def update(movieId: String): Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val movie: Movie = Await.result(movieRepository.getById(movieId), Duration.Inf).get

    val actors: Seq[Actor] = Await.result(actorRepository.getAll(), Duration.Inf)
    val selectedActors: Seq[String] = Await.result(actorRepository.getForMovie(movieId), Duration.Inf).map(_.id)

    val directors: Seq[Director] = Await.result(directorRepository.getAll(), Duration.Inf)
    val selectedDirectors: Seq[String] = Await.result(directorRepository.getForMovie(movieId), Duration.Inf).map(_.id)

    val genres: Seq[Genre] = Await.result(genreRepository.getAll(), Duration.Inf)
    val selectedGenres: Seq[String] = Await.result(genreRepository.getForMovie(movieId), Duration.Inf).map(_.id)

    val updateForm = createMovieForm.fill(
      CreateMovieForm(movie.title, movie.description, movie.productionYear, movie.price, movie.img, selectedActors, selectedDirectors, selectedGenres)
    )

    Ok(views.html.movie.update_movie(
      movieId, updateForm,
      selectedActors, actors,
      selectedDirectors, directors,
      selectedGenres, genres))
  }

  def updateMovieHandler(movieId: String): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateMovieForm] =>
      Future {
        Redirect(routes.MovieController.update(movieId)).flashing("error" -> "Błąd podczas edycji filmu!")
      }
    }

    val successFunction = { movie: CreateMovieForm =>
      movieRepository.update(movieId, movie.title, movie.description, movie.productionYear, movie.price, movie.img, movie.actors, movie.directors, movie.genres).map { _ =>
        Redirect(routes.MovieController.getAll()).flashing("success" -> "Film zmodyfikowany!")
      }
    }
    createMovieForm.bindFromRequest.fold(errorFunction, successFunction)
  }
}

case class CreateMovieForm(title: String,
                           description: String,
                           productionYear: String,
                           price: Double,
                           img: String,
                           actors: Seq[String],
                           directors: Seq[String],
                           genres: Seq[String]
                          )

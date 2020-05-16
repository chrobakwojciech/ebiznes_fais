package controllers

import javax.inject.{Inject, Singleton}
import models.{Genre, Movie}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.{GenreRepository, MovieRepository}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class GenreController @Inject()(genreRepository: GenreRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val createGenreForm: Form[CreateGenreForm] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CreateGenreForm.apply)(CreateGenreForm.unapply)
  }

  def getAll = Action.async { implicit request =>
    val genres = genreRepository.getAll()
    genres.map(genre => Ok(views.html.genre.genres(genre)))
  }

  def get(genreId: String) = Action.async { implicit request =>
    val movies: Seq[Movie] = Await.result(movieRepository.getForGenre(genreId), Duration.Inf)

    genreRepository.getById(genreId) map {
      case Some(g) => Ok(views.html.genre.genre(g, movies))
      case None => Redirect(routes.GenreController.getAll())
    }
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.genre.add_genre(createGenreForm))
  }


  def createGenreHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateGenreForm] =>
      Future {
        Redirect(routes.GenreController.create()).flashing("error" -> "Błąd podczas dodawania gatunku!")
      }
    }

    val successFunction = { genre: CreateGenreForm =>
      genreRepository.create(genre.name).map { _ =>
        Redirect(routes.GenreController.getAll()).flashing("success" -> "Gatunek dodany!")
      };
    }
    createGenreForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(genreId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    genreRepository.delete(genreId).map(_ => Redirect(routes.GenreController.getAll()).flashing("info" -> "Gatunek usunięty!"))
  }

  def update(genreId: String) = Action { implicit request: MessagesRequest[AnyContent] =>
    val genre: Genre = Await.result(genreRepository.getById(genreId), Duration.Inf).get
    val updateForm = createGenreForm.fill(CreateGenreForm(genre.name))
    Ok(views.html.genre.update_genre(genreId, updateForm))
  }

  def updateGenreHandler(genreId: String): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateGenreForm] =>
      Future {
        Redirect(routes.GenreController.update(genreId)).flashing("error" -> "Błąd podczas edycji gatunku!")
      }
    }

    val successFunction = { genre: CreateGenreForm =>
      genreRepository.update(genreId, genre.name).map { _ =>
        Redirect(routes.GenreController.getAll()).flashing("success" -> "Gatunek zmodyfikowany!")
      };
    }
    createGenreForm.bindFromRequest.fold(errorFunction, successFunction)
  }

}

case class CreateGenreForm(name: String)
package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.auth.UserRoles
import models.{Genre, Movie}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.{GenreRepository, MovieRepository}
import utils.auth.{CookieEnv, RoleCookieAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class GenreController @Inject()(genreRepository: GenreRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents,
                                silhouette: Silhouette[CookieEnv])(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val createGenreForm: Form[CreateGenreForm] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CreateGenreForm.apply)(CreateGenreForm.unapply)
  }

  def getAll = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val genres = genreRepository.getAll()
    genres.map(genre => Ok(views.html.genre.genres(genre)))
  }

  def get(genreId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val movies: Seq[Movie] = Await.result(movieRepository.getForGenre(genreId), Duration.Inf)

    genreRepository.getById(genreId) map {
      case Some(g) => Ok(views.html.genre.genre(g, movies))
      case None => Redirect(routes.GenreController.getAll())
    }
  }

  def create = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_] =>
    Ok(views.html.genre.add_genre(createGenreForm))
  }


  def createGenreHandler: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
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

  def delete(genreId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    genreRepository.delete(genreId).map(_ => Redirect(routes.GenreController.getAll()).flashing("info" -> "Gatunek usunięty!"))
  }

  def update(genreId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val genre: Genre = Await.result(genreRepository.getById(genreId), Duration.Inf).get
    val updateForm = createGenreForm.fill(CreateGenreForm(genre.name))
    Ok(views.html.genre.update_genre(genreId, updateForm))
  }

  def updateGenreHandler(genreId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
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
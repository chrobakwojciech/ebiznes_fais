package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.{Director, Movie, UserRoles}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.{DirectorRepository, MovieRepository}
import utils.auth.{CookieEnv, RoleCookieAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class DirectorController @Inject()(directorRepository: DirectorRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents,
                                   silhouette: Silhouette[CookieEnv])(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createDirectorForm: Form[CreateDirectorForm] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "img" -> nonEmptyText
    )(CreateDirectorForm.apply)(CreateDirectorForm.unapply)
  }

  def getAll = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val directors = directorRepository.getAll()
    directors.map(director => Ok(views.html.director.directors(director)))
  }

  def get(directorId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val movies: Seq[Movie] = Await.result(movieRepository.getForDirector(directorId), Duration.Inf)

    directorRepository.getById(directorId) map {
      case Some(d) => Ok(views.html.director.director(d, movies))
      case None => Redirect(routes.DirectorController.getAll())
    }
  }

  def create = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    Ok(views.html.director.add_director(createDirectorForm))
  }

  def createDirectorHandler: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    val errorFunction = { formWithErrors: Form[CreateDirectorForm] =>
      Future {
        Redirect(routes.DirectorController.create()).flashing("error" -> "Błąd podczas dodawania reżysera!")
      }
    }

    val successFunction = { director: CreateDirectorForm =>
      directorRepository.create(director.firstName, director.lastName, director.img).map { _ =>
        Redirect(routes.DirectorController.getAll()).flashing("success" -> "Reżyser dodany!")
      };
    }
    createDirectorForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(directorId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    directorRepository.delete(directorId).map(_ => Redirect(routes.DirectorController.getAll()).flashing("info" -> "Reżyser usunięty!"))
  }

  def update(directorId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val director: Director = Await.result(directorRepository.getById(directorId), Duration.Inf).get
    val updateForm = createDirectorForm.fill(CreateDirectorForm(director.firstName, director.lastName, director.img))
    Ok(views.html.director.update_director(directorId, updateForm))
  }

  def updateDirectorHandler(directorId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    val errorFunction = { formWithErrors: Form[CreateDirectorForm] =>
      Future {
        Redirect(routes.DirectorController.update(directorId)).flashing("error" -> "Błąd podczas edycji reżysera!")
      }
    }

    val successFunction = { director: CreateDirectorForm =>
      directorRepository.update(directorId, director.firstName, director.lastName, director.img).map { _ =>
        Redirect(routes.DirectorController.getAll()).flashing("success" -> "Reżyser zmodyfikowany!")
      };
    }
    createDirectorForm.bindFromRequest.fold(errorFunction, successFunction)
  }


}

case class CreateDirectorForm(firstName: String,
                              lastName: String,
                              img: String)
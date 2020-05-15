package controllers

import javax.inject.{Inject, Singleton}
import models.Movie
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.{DirectorRepository, MovieRepository}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class DirectorController @Inject()(directorRepository: DirectorRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createDirectorForm: Form[CreateDirectorForm] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "img" -> nonEmptyText
    )(CreateDirectorForm.apply)(CreateDirectorForm.unapply)
  }

  def getAll = Action.async { implicit request =>
    val directors = directorRepository.getAll()
    directors.map(director => Ok(views.html.director.directors(director)))
  }

  def get(directorId: String) = Action.async { implicit request =>
    val movies: Seq[Movie] = Await.result(movieRepository.getForDirector(directorId), Duration.Inf)

    directorRepository.getById(directorId) map {
      case Some(d) => Ok(views.html.director.director(d, movies))
      case None => Redirect(routes.DirectorController.getAll())
    }
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.director.add_director(createDirectorForm))
  }

  def createDirectorHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
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

  def delete(directorId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    directorRepository.delete(directorId).map(_ => Redirect(routes.DirectorController.getAll()).flashing("info" -> "Reżyser usunięty!"))
  }


}
case class CreateDirectorForm(firstName: String,
                           lastName: String,
                           img: String)
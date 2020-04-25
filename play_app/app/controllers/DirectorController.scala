package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import repositories.DirectorRepository

import scala.concurrent.ExecutionContext

@Singleton
class DirectorController @Inject()(directorRepository: DirectorRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll = Action.async { implicit request =>
    val directors = directorRepository.getAll()
    directors.map(director => Ok(views.html.director.directors(director)))
  }

  def get(directorId: String) = Action.async { implicit request =>
    directorRepository.getById(directorId) map {
      case Some(d) => Ok(views.html.director.director(d))
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

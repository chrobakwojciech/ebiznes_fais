package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import repositories.GenreRepository

import scala.concurrent.ExecutionContext

@Singleton
class GenreController @Inject()(genreRepository: GenreRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll = Action.async { implicit request =>
    val genres = genreRepository.getAll()
    genres.map(genre => Ok(views.html.genre.genres(genre)))
  }

  def get(genreId: String) = Action.async { implicit request =>
    genreRepository.getById(genreId) map {
      case Some(g) => Ok(views.html.genre.genre(g))
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

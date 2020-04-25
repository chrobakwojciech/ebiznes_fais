package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repositories.CommentRepository

import scala.concurrent.ExecutionContext

@Singleton
class CommentController @Inject()(commentRepository: CommentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll = Action.async { implicit request =>
    val comments = commentRepository.getAllWithMovieAndUser()
    comments.map(comment => Ok(views.html.comment.comments(comment)))
  }

  def create = Action {
    Ok("")
  }

  def update(commentId: String) = Action {
    Ok("")
  }

  def delete(commentId: String) = Action {
    Ok("")
  }


}

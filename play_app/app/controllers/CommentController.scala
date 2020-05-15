package controllers

import javax.inject.{Inject, Singleton}
import models.{Movie, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.{CommentRepository, MovieRepository, UserRepository}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class CommentController @Inject()(commentRepository: CommentRepository, userRepository: UserRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val createCommentForm: Form[CreateCommentForm] = Form {
    mapping(
      "content" -> nonEmptyText,
      "userId" -> nonEmptyText,
      "movieId" -> nonEmptyText
    )(CreateCommentForm.apply)(CreateCommentForm.unapply)
  }

  def getAll = Action.async { implicit request =>
    val comments = commentRepository.getAllWithMovieAndUser()
    comments.map(comment => Ok(views.html.comment.comments(comment)))
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf);
    Ok(views.html.comment.add_comment(createCommentForm, users, movies))
  }

  def createCommentHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateCommentForm] =>
      Future {
        Redirect(routes.CommentController.create()).flashing("error" -> "Błąd podczas dodawania komentarza!")
      }
    }

    val successFunction = { comment: CreateCommentForm =>
      commentRepository.create(comment.content, comment.userId, comment.movieId).map { _ =>
        Redirect(routes.CommentController.getAll()).flashing("success" -> "Komentarz dodany!")
      };
    }
    createCommentForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(commentId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    commentRepository.delete(commentId).map(_ => Redirect(routes.CommentController.getAll()).flashing("info" -> "Komentarz został usunięty!"))
  }

}

case class CreateCommentForm(content: String,
                   userId: String,
                   movieId: String
                  )
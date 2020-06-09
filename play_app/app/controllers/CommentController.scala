package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.{Comment, Movie, User, UserRoles}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.{CommentRepository, MovieRepository, UserRepository}
import utils.auth.{CookieEnv, RoleCookieAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class CommentController @Inject()(commentRepository: CommentRepository, userRepository: UserRepository, movieRepository: MovieRepository,
                                  silhouette: Silhouette[CookieEnv], cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val createCommentForm: Form[CreateCommentForm] = Form {
    mapping(
      "content" -> nonEmptyText,
      "userId" -> nonEmptyText,
      "movieId" -> nonEmptyText
    )(CreateCommentForm.apply)(CreateCommentForm.unapply)
  }

  def getAll = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request =>
    val comments = commentRepository.getAllWithMovieAndUser()
    comments.map(comment => Ok(views.html.comment.comments(comment)))
  }

  def create = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf);
    Ok(views.html.comment.add_comment(createCommentForm, users, movies))
  }

  def createCommentHandler: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
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

  def delete(commentId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    commentRepository.delete(commentId).map(_ => Redirect(routes.CommentController.getAll()).flashing("info" -> "Komentarz został usunięty!"))
  }

  def update(commentId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf);
    val comment: Comment = Await.result(commentRepository.getById(commentId), Duration.Inf).get
    val updateForm = createCommentForm.fill(CreateCommentForm(comment.content, comment.user, comment.movie))
    Ok(views.html.comment.update_comment(commentId, updateForm, users, movies))
  }

  def updateCommentHandler(commentId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    val errorFunction = { formWithErrors: Form[CreateCommentForm] =>
      Future {
        Redirect(routes.CommentController.update(commentId)).flashing("error" -> "Błąd podczas edycji komentarza!")
      }
    }

    val successFunction = { comment: CreateCommentForm =>
      commentRepository.update(commentId, comment.content, comment.userId, comment.movieId).map { _ =>
        Redirect(routes.CommentController.getAll()).flashing("success" -> "Komentarz zmodyfikowany!")
      };
    }
    createCommentForm.bindFromRequest.fold(errorFunction, successFunction)
  }

}

case class CreateCommentForm(content: String,
                   userId: String,
                   movieId: String
                  )
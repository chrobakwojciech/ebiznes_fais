package controllers.api

import javax.inject.{Inject, Singleton}
import models.{Comment, Movie, User}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{CommentRepository, MovieRepository, UserRepository}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

case class CreateComment(content: String,
                       user: String,
                       movie: String)

object CreateComment {
  implicit val commentFormat = Json.format[CreateComment]
}


case class UpdateComment(content: Option[String],
                         user: Option[String],
                         movie: Option[String])

object UpdateComment {
  implicit val commentFormat = Json.format[UpdateComment]
}


@Singleton
class CommentApiController @Inject()(commentRepository: CommentRepository, userRepository: UserRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val comments: Future[Seq[(Comment, Movie, User)]] = commentRepository.getAllWithMovieAndUser()
    comments.map(comment => {
      val newComment = comment.map {
        case (c, m, u) => Json.obj("id" -> c.id, "content" -> c.content, "movie" -> m, "user" -> u)
      }
      Ok(Json.toJson(newComment))
    })
  }

  def get(id: String) = Action.async { implicit request =>
    commentRepository.getByIdWithMovieAndUser(id) map {
      case Some(c) => Ok(Json.toJson(Json.obj("id" -> c._1.id, "content" -> c._1.content, "movie" -> c._2, "user" -> c._3)))
      case None => NotFound(Json.obj("message" -> "Comment does not exist"))
    }
  }

  def create() = Action(parse.json) { implicit request =>
    val body = request.body
    body.validate[CreateComment].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      comment => {
        // @FIXME
        val userExist: Boolean = Await.result(userRepository.isExist(comment.user), Duration.Inf)
        if (!userExist) {
          BadRequest(Json.obj("message" -> "User does not exist"))
        } else {
          val movieExist: Boolean = Await.result(movieRepository.isExist(comment.movie), Duration.Inf)
          if (!movieExist) {
            BadRequest(Json.obj("message" -> "Movie does not exist"))
          } else {
            commentRepository.create(comment.content, comment.user, comment.movie)
            Ok(Json.obj("message" -> "Comment created"))
          }
        }
      }
    )
  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
    commentRepository.getById(id) map {
      case Some(c) => {
        val body = request.body
        body.validate[UpdateComment].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          comment => {
            // @FIXME
            val userExist: Boolean = Await.result(userRepository.isExist(comment.user.getOrElse(c.user)), Duration.Inf)
            if (!userExist) {
              BadRequest(Json.obj("message" -> "User does not exist"))
            } else {
              val movieExist: Boolean = Await.result(movieRepository.isExist(comment.movie.getOrElse(c.movie)), Duration.Inf)
              if (!movieExist) {
                BadRequest(Json.obj("message" -> "Movie does not exist"))
              } else {
                commentRepository.update(id, comment.content.getOrElse(c.content), comment.user.getOrElse(c.user), comment.movie.getOrElse(c.movie))
                Ok(Json.obj("message" -> "Comment updated"))
              }
            }
          }
        )
      }
      case None => NotFound(Json.obj("message" -> "Comment does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    commentRepository.getById(id) map {
      case Some(c) => {
        commentRepository.delete(id)
        Ok(Json.obj("message" -> "Comment deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Comment does not exist"))
    }
  }

}

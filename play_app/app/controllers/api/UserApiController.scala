package controllers.api

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories._
import utils.auth.{JsonErrorHandler, JwtEnv}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class UserApiController @Inject()(movieRepository: MovieRepository,
                                   actorRepository: ActorRepository,
                                   directorRepository: DirectorRepository,
                                   genreRepository: GenreRepository,
                                   commentRepository: CommentRepository,
                                   ratingRepository: RatingRepository,
                                   cc: MessagesControllerComponents,
                                   errorHandler: JsonErrorHandler,
                                   silhouette: Silhouette[JwtEnv]
                                  )(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getMovies = silhouette.SecuredAction(errorHandler).async { implicit request =>
    val movies = movieRepository.getForUser(request.identity.id)
    movies.map(movie => Ok(Json.toJson(movie)))
  }

}

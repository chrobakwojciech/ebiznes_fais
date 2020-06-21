package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.auth.{User, UserRoles}
import models.{Movie, Rating}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc._
import repositories.auth.UserService
import repositories.{MovieRepository, RatingRepository}
import utils.auth.{CookieEnv, RoleCookieAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class RatingController @Inject()(ratingRepository: RatingRepository, userRepository: UserService, movieRepository: MovieRepository, cc: MessagesControllerComponents,
                                 silhouette: Silhouette[CookieEnv])(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createRatingForm: Form[CreateRatingForm] = Form {
    mapping(
      "value" -> of(intFormat),
      "userId" -> nonEmptyText,
      "movieId" -> nonEmptyText
    )(CreateRatingForm.apply)(CreateRatingForm.unapply)
  }

  def getAll = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val ratings = ratingRepository.getAllWithMovieAndUser()
    ratings.map(rating => Ok(views.html.rating.ratings(rating)))
  }

  def create = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf);
    Ok(views.html.rating.add_rating(createRatingForm, users, movies))
  }

  def createRatingHandler: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    val errorFunction = { formWithErrors: Form[CreateRatingForm] =>
      Future {
        Redirect(routes.RatingController.create()).flashing("error" -> "Błąd podczas dodawania oceny!")
      }
    }

    val successFunction = { rating: CreateRatingForm =>
      ratingRepository.create(rating.value, rating.userId, rating.movieId).map { _ =>
        Redirect(routes.RatingController.getAll()).flashing("success" -> "Ocena dodana!")
      };
    }
    createRatingForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(ratingId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    ratingRepository.delete(ratingId).map(_ => Redirect(routes.RatingController.getAll()).flashing("info" -> "Ocena filmu została usunięta!"))
  }


  def update(ratingId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf);
    val rating: Rating = Await.result(ratingRepository.getById(ratingId), Duration.Inf).get
    val updateForm = createRatingForm.fill(CreateRatingForm(rating.value, rating.user, rating.movie))
    Ok(views.html.rating.update_rating(ratingId, updateForm, users, movies))
  }

  def updateRatingHandler(ratingId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    val errorFunction = { formWithErrors: Form[CreateRatingForm] =>
      Future {
        Redirect(routes.RatingController.update(ratingId)).flashing("error" -> "Błąd podczas edycji oceny filmu!")
      }
    }

    val successFunction = { rating: CreateRatingForm =>
      ratingRepository.update(ratingId, rating.value, rating.userId, rating.movieId).map { _ =>
        Redirect(routes.RatingController.getAll()).flashing("success" -> "Ocena zmodyfikowana!")
      };
    }
    createRatingForm.bindFromRequest.fold(errorFunction, successFunction)
  }

}

case class CreateRatingForm(value: Int,
                            userId: String,
                            movieId: String)

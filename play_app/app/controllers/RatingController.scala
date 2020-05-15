package controllers

import javax.inject.{Inject, Singleton}
import models.{Movie, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc._
import repositories.{MovieRepository, RatingRepository, UserRepository}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class RatingController @Inject()(ratingRepository: RatingRepository, userRepository: UserRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createRatingForm: Form[CreateRatingForm] = Form {
    mapping(
      "value" -> of(intFormat),
      "userId" -> nonEmptyText,
      "movieId" -> nonEmptyText
    )(CreateRatingForm.apply)(CreateRatingForm.unapply)
  }

  def getAll = Action.async { implicit request =>
    val ratings = ratingRepository.getAllWithMovieAndUser()
    ratings.map(rating => Ok(views.html.rating.ratings(rating)))
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf);
    Ok(views.html.rating.add_rating(createRatingForm, users, movies))
  }

  def createRatingHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
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

  def delete(ratingId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    ratingRepository.delete(ratingId).map(_ => Redirect(routes.RatingController.getAll()).flashing("info" -> "Ocena filmu została usunięta!"))
  }
}

case class CreateRatingForm(value: Int,
                            movieId: String,
                            userId: String)

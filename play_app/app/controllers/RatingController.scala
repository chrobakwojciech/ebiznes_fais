package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import repositories.RatingRepository

import scala.concurrent.ExecutionContext

@Singleton
class RatingController @Inject()(ratingRepository: RatingRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {


  def getAll = Action.async { implicit request =>
    val ratings = ratingRepository.getAllWithMovieAndUser()
    ratings.map(rating => Ok(views.html.rating.ratings(rating)))
  }
  def create = Action {
    Ok("")
  }

  def update(ratingId: String) = Action {
    Ok("")
  }

  def delete(ratingId: String) = Action {
    Ok("")
  }
}

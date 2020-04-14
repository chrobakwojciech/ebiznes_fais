package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class RatingController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

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

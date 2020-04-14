package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class MovieController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAll = Action {
    Ok("")
  }

  def get(movieId: String) = Action {
    Ok("")
  }

  def create = Action {
    Ok("")
  }

  def update(movieId: String) = Action {
    Ok("")
  }

  def delete(movieId: String) = Action {
    Ok("")
  }


}

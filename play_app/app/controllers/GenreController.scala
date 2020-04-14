package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class GenreController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAll = Action {
    Ok("")
  }

  def get(genreId: String) = Action {
    Ok("")
  }

  def create = Action {
    Ok("")
  }

  def update(genreId: String) = Action {
    Ok("")
  }

  def delete(genreId: String) = Action {
    Ok("")
  }


}

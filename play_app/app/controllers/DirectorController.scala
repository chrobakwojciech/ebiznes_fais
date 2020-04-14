package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class DirectorController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAll = Action {
    Ok("")
  }

  def get(directorId: String) = Action {
    Ok("")
  }

  def create = Action {
    Ok("")
  }

  def update(directorId: String) = Action {
    Ok("")
  }

  def delete(directorId: String) = Action {
    Ok("")
  }


}

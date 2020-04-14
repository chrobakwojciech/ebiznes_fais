package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAll = Action {
    Ok("")
  }

  def get(userId: String) = Action {
    Ok("")
  }

  def create = Action {
    Ok("")
  }

  def update(userId: String) = Action {
    Ok("")
  }

  def delete(userId: String) = Action {
    Ok("")
  }


}

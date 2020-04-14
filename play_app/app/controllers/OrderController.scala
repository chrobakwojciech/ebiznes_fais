package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class OrderController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def get(userId: String) = Action {
    Ok("")
  }

  def create = Action {
    Ok("")
  }

  def update(actorId: String) = Action {
    Ok("")
  }

  def delete(actorId: String) = Action {
    Ok("")
  }


}

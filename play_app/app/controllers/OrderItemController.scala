package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class OrderItemController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def get(orderId: String) = Action {
    Ok("")
  }

  def getForUser(userId: String) = Action {
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

package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ActorController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAll = Action {
    Ok("")
  }

  def get(actorId: String) = Action {
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

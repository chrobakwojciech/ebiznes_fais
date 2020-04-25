package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import repositories.ActorRepository

import scala.concurrent.ExecutionContext

@Singleton
class ActorController @Inject()(actorRepository: ActorRepository, cc: MessagesControllerComponents) (implicit ec: ExecutionContext) extends MessagesAbstractController(cc)  {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val actors = actorRepository.getAll();
    actors.map(actor => Ok(views.html.actor.actors(actor)))
  }

  def get(actorId: String) = Action.async { implicit request =>
    actorRepository.getById(actorId) map {
      case Some(a) => Ok(views.html.actor.actor(a))
      case None => Redirect(routes.ActorController.getAll())
    }
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

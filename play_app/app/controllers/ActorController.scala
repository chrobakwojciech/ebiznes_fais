package controllers

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import models.Movie
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}
import repositories.{ActorRepository, MovieRepository}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class ActorController @Inject()(actorRepository: ActorRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createActorForm: Form[CreateActorForm] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "img" -> nonEmptyText
    )(CreateActorForm.apply)(CreateActorForm.unapply)
  }

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val actors = actorRepository.getAll();
    actors.map(actor => Ok(views.html.actor.actors(actor)))
  }

  def get(actorId: String) = Action.async { implicit request =>
    var movies: Seq[Movie] = Seq[Movie]()
    movieRepository.getForActor(actorId).onComplete {
      case Success(m) => movies = m
      case Failure(_) => print("fail")
    }

    actorRepository.getById(actorId) map {
      case Some(a) => Ok(views.html.actor.actor(a, movies))
      case None => Redirect(routes.ActorController.getAll())
    }
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.actor.add_actor(createActorForm))
  }

  def createActorHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateActorForm] =>
      Future {
        Redirect(routes.ActorController.create()).flashing("error" -> "Błąd podczas dodawania aktora!")
      }
    }

    val successFunction = { actor: CreateActorForm =>
      actorRepository.create(actor.firstName, actor.lastName, actor.img).map { _ =>
        Redirect(routes.ActorController.getAll()).flashing("success" -> "Aktor dodany!")
      };
    }
    createActorForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(actorId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    actorRepository.delete(actorId).map(_ => Redirect(routes.ActorController.getAll()).flashing("info" -> "Aktor usunięty!"))
  }
}

case class CreateActorForm(firstName: String,
                           lastName: String,
                           img: String)
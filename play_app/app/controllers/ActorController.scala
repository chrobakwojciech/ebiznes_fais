package controllers

import javax.inject.{Inject, Singleton}
import models.Movie
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}
import repositories.{ActorRepository, MovieRepository}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class ActorController @Inject()(actorRepository: ActorRepository, movieRepository: MovieRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

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

package controllers

import com.mohiva.play.silhouette.api.{HandlerResult, Silhouette}
import com.mohiva.play.silhouette.api.actions.{SecuredErrorHandler, SecuredRequest, SecuredRequestHeader}
import javax.inject.{Inject, Singleton}
import models.auth.UserRoles
import models.{Actor, Movie}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.Results.Redirect
import play.api.mvc._
import repositories.{ActorRepository, MovieRepository}
import utils.auth.{CookieEnv, DashboardErrorHandler, JsonErrorHandler, JwtEnv, RoleCookieAuthorization, RoleJWTAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class ActorController @Inject()(
                                 actorRepository: ActorRepository,
                                 movieRepository: MovieRepository,
                                 cc: MessagesControllerComponents,
                                 silhouette: Silhouette[CookieEnv])
                               (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createActorForm: Form[CreateActorForm] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "img" -> nonEmptyText
    )(CreateActorForm.apply)(CreateActorForm.unapply)
  }

  def getAll: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val actors = actorRepository.getAll();
    actors.map(actor => Ok(views.html.actor.actors(actor)))
  }

  def get(actorId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val movies: Seq[Movie] = Await.result(movieRepository.getForActor(actorId), Duration.Inf)

    actorRepository.getById(actorId) map {
      case Some(a) => Ok(views.html.actor.actor(a, movies))
      case None => Redirect(routes.ActorController.getAll())
    }
  }

  def create = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    Ok(views.html.actor.add_actor(createActorForm))
  }

  def createActorHandler: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
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

  def delete(actorId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    actorRepository.delete(actorId).map(_ => Redirect(routes.ActorController.getAll()).flashing("info" -> "Aktor usunięty!"))
  }

  def update(actorId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val actor: Actor = Await.result(actorRepository.getById(actorId), Duration.Inf).get
    val updateForm = createActorForm.fill(CreateActorForm(actor.firstName, actor.lastName, actor.img))
    Ok(views.html.actor.update_actor(actorId, updateForm))
  }

  def updateActorHandler(actorId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    val errorFunction = { formWithErrors: Form[CreateActorForm] =>
      Future {
        Redirect(routes.ActorController.update(actorId)).flashing("error" -> "Błąd podczas edycji aktora!")
      }
    }

    val successFunction = { actor: CreateActorForm =>
      actorRepository.update(actorId, actor.firstName, actor.lastName, actor.img).map { _ =>
        Redirect(routes.ActorController.getAll()).flashing("success" -> "Aktor zmodyfikowany!")
      };
    }
    createActorForm.bindFromRequest.fold(errorFunction, successFunction)
  }
}

case class CreateActorForm(firstName: String,
                           lastName: String,
                           img: String)
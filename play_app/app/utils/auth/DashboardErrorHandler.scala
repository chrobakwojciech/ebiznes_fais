package utils.auth

import com.mohiva.play.silhouette.api.actions.SecuredErrorHandler
import controllers.routes
import javax.inject.Singleton
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class DashboardErrorHandler extends SecuredErrorHandler {
  override def onNotAuthenticated(implicit request: RequestHeader) = {
    Future.successful(Redirect(routes.HomeController.index()).flashing("error" -> "Błąd autentykacji!"))
  }

  override def onNotAuthorized(implicit request: RequestHeader) = {
    Future.successful(Redirect(routes.HomeController.index()).flashing("error" -> "Błąd autoryzacji!"))
  }
}
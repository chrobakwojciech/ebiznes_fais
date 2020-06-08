package utils.auth

import com.atlassian.jwt.core.http.auth.JwtAuthenticator
import com.mohiva.play.silhouette.api.{Authenticator, Authorization}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import models.User
import models.UserRoles.UserRole
import play.api.mvc.Request

import scala.concurrent.Future


case class RoleAuthorization(role: UserRole) extends Authorization[User, JWTAuthenticator] {
  def isAuthorized[B](user: User, authenticator: JWTAuthenticator)(implicit request: Request[B]) = {
    Future.successful(user.role == role)
  }
}

package controllers.api

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.UserRoles
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.PaymentRepository
import utils.auth.{JsonErrorHandler, JwtEnv, RoleJWTAuthorization}

import scala.concurrent.ExecutionContext

case class CreatePayment(name: String)

object CreatePayment {
  implicit val paymentFormat = Json.format[CreatePayment]
}


case class UpdatePayment(name: Option[String])

object UpdatePayment {
  implicit val paymentFormat = Json.format[UpdatePayment]
}


@Singleton
class PaymentApiController @Inject()(
                                      paymentRepository: PaymentRepository,
                                      errorHandler: JsonErrorHandler,
                                      silhouette: Silhouette[JwtEnv],
                                      cc: MessagesControllerComponents
                                    )(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val paymentNotFound = NotFound(Json.obj("message" -> "Payment does not exist"))

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val payments = paymentRepository.getAll()
    payments.map(payment => Ok(Json.toJson(payment)))
  }

  def get(id: String) = Action.async { implicit request =>
    paymentRepository.getById(id) map {
      case Some(p) => Ok(Json.toJson(p))
      case None => paymentNotFound
    }
  }

  def create() = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)) { implicit request =>
    val body = request.body.asJson.get
    body.validate[CreatePayment].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      payment => {
        paymentRepository.create(payment.name)
        Ok(Json.obj("message" -> "Payment created"))
      }
    )
  }

  def update(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async(parse.json) { implicit request =>
    paymentRepository.getById(id) map {
      case Some(p) => {
        val body = request.body
        body.validate[UpdatePayment].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          payment => {
            paymentRepository.update(id, payment.name.getOrElse(p.name))
            Ok(Json.obj("message" -> "Payment updated"))
          }
        )
      }
      case None => paymentNotFound
    }
  }

  def delete(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async { implicit request =>
    paymentRepository.getById(id) map {
      case Some(g) => {
        paymentRepository.delete(id)
        Ok(Json.obj("message" -> "Payment deleted"))
      }
      case None => paymentNotFound
    }
  }

}

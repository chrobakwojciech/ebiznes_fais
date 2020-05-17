package controllers.api

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories.{GenreRepository, PaymentRepository}

import scala.concurrent.ExecutionContext

case class CreatePayment(name: String)

object CreatePayment {
  implicit val userFormat = Json.format[CreatePayment]
}


case class UpdatePayment(name: Option[String])

object UpdatePayment {
  implicit val userFormat = Json.format[UpdatePayment]
}


@Singleton
class PaymentApiController @Inject()(paymentRepository: PaymentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val payments = paymentRepository.getAll()
    payments.map(payment => Ok(Json.toJson(payment)))
  }

  def get(id: String) = Action.async { implicit request =>
    paymentRepository.getById(id) map {
      case Some(p) => Ok(Json.toJson(p))
      case None => NotFound(Json.obj("message" -> "Payment does not exist"))
    }
  }

  def create() = Action(parse.json) { implicit request =>
    val body = request.body
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

  def update(id: String) = Action.async(parse.json) { implicit request =>
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
      case None => NotFound(Json.obj("message" -> "Payment does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    paymentRepository.getById(id) map {
      case Some(g) => {
        paymentRepository.delete(id)
        Ok(Json.obj("message" -> "Payment deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Payment does not exist"))
    }
  }

}

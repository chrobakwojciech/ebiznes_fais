package controllers

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.PaymentRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentController @Inject()(paymentRepository: PaymentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createPaymentForm: Form[CreatePaymentForm] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CreatePaymentForm.apply)(CreatePaymentForm.unapply)
  }

  def getAll = Action.async { implicit request =>
    val payments = paymentRepository.getAll();
    payments.map(payment => Ok(views.html.payment.payments(payment)))
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.payment.add_payment(createPaymentForm))
  }

  def createPaymentHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreatePaymentForm] =>
      Future {
        Redirect(routes.PaymentController.create()).flashing("error" -> "Błąd podczas dodawania typu płatności!")
      }
    }

    val successFunction = { payment: CreatePaymentForm =>
      paymentRepository.create(payment.name).map { _ =>
        Redirect(routes.PaymentController.getAll()).flashing("success" -> "Typ płatności dodany!")
      };
    }
    createPaymentForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(paymentId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    paymentRepository.delete(paymentId).map(_ => Redirect(routes.PaymentController.getAll()).flashing("info" -> "Typ płatności usunięty!"))
  }

}
case class CreatePaymentForm(name: String)

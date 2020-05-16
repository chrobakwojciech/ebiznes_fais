package controllers

import javax.inject.{Inject, Singleton}
import models.Payment
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.PaymentRepository

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

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

  def update(paymentId: String) = Action { implicit request: MessagesRequest[AnyContent] =>
    val payment: Payment = Await.result(paymentRepository.getById(paymentId), Duration.Inf).get
    val updateForm = createPaymentForm.fill(CreatePaymentForm(payment.name))
    Ok(views.html.payment.update_payment(paymentId, updateForm))
  }

  def updatePaymentHandler(paymentId: String): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreatePaymentForm] =>
      Future {
        Redirect(routes.PaymentController.update(paymentId)).flashing("error" -> "Błąd podczas edycji typu płatności!")
      }
    }

    val successFunction = { payment: CreatePaymentForm =>
      paymentRepository.update(paymentId, payment.name).map { _ =>
        Redirect(routes.PaymentController.getAll()).flashing("success" -> "Typ płatności zmodyfikowany!")
      };
    }
    createPaymentForm.bindFromRequest.fold(errorFunction, successFunction)
  }

}
case class CreatePaymentForm(name: String)

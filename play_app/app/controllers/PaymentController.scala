package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.Payment
import models.auth.UserRoles
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.PaymentRepository
import utils.auth.{CookieEnv, RoleCookieAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class PaymentController @Inject()(paymentRepository: PaymentRepository, cc: MessagesControllerComponents,
                                  silhouette: Silhouette[CookieEnv])(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createPaymentForm: Form[CreatePaymentForm] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CreatePaymentForm.apply)(CreatePaymentForm.unapply)
  }

  def getAll = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.User)).async { implicit request: Request[_]  =>
    val payments = paymentRepository.getAll();
    payments.map(payment => Ok(views.html.payment.payments(payment)))
  }

  def create = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    Ok(views.html.payment.add_payment(createPaymentForm))
  }

  def createPaymentHandler: Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
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

  def delete(paymentId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
    paymentRepository.delete(paymentId).map(_ => Redirect(routes.PaymentController.getAll()).flashing("info" -> "Typ płatności usunięty!"))
  }

  def update(paymentId: String) = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)) { implicit request: Request[_]  =>
    val payment: Payment = Await.result(paymentRepository.getById(paymentId), Duration.Inf).get
    val updateForm = createPaymentForm.fill(CreatePaymentForm(payment.name))
    Ok(views.html.payment.update_payment(paymentId, updateForm))
  }

  def updatePaymentHandler(paymentId: String): Action[AnyContent] = silhouette.SecuredAction(RoleCookieAuthorization(UserRoles.Admin)).async { implicit request: Request[_]  =>
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

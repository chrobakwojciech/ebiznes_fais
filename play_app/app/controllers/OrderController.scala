package controllers

import javax.inject.{Inject, Singleton}
import models.{Comment, Movie, Order, OrderItem, Payment, User}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}
import repositories.{MovieRepository, OrderRepository, PaymentRepository, UserRepository}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class OrderController @Inject()(orderRepository: OrderRepository, movieRepository: MovieRepository, userRepository: UserRepository, paymentRepository: PaymentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createOrderForm: Form[CreateOrderForm] = Form {
    mapping(
      "user" -> nonEmptyText,
      "payment" -> nonEmptyText,
      "movies" -> seq(nonEmptyText)
    )(CreateOrderForm.apply)(CreateOrderForm.unapply)
  }

  def getAll() = Action.async { implicit request =>
    val orders = orderRepository.getAllWithPaymentAndUser()
    orders.map(order => Ok(views.html.order.orders(order)))
  }

  def get(orderId: String) = Action.async { implicit request =>
    var orderItems: Seq[(OrderItem, Movie)] = Seq[(OrderItem, Movie)]()
    var sum: Double = 0.0
    orderRepository.getOrderItemsWithMovie(orderId).onComplete {
      case Success(oi) => {
        oi.foreach(sum += _._1.price);
        sum = (math.floor(sum * 100)/100)
        orderItems = oi
      }
      case Failure(_) => print("fail")
    }

    orderRepository.getByIdWithUserAndPayment(orderId) map {
      case Some(o) => Ok(views.html.order.order(o, orderItems, sum))
      case None => Redirect(routes.OrderController.getAll())
    }
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val payments: Seq[Payment] = Await.result(paymentRepository.getAll(), Duration.Inf)
    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf)
    Ok(views.html.order.add_order(createOrderForm, users, payments, movies))
  }

  def createOrderHandler: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateOrderForm] =>
      Future {
        Redirect(routes.OrderController.create()).flashing("error" -> "Błąd podczas składania zamówienia!")
      }
    }

    val successFunction = { order: CreateOrderForm =>
      orderRepository.create(order.user, order.payment, order.movies).map { _ =>
        Redirect(routes.OrderController.getAll()).flashing("success" -> "Zamówienie złożone!")
      };
    }
    createOrderForm.bindFromRequest.fold(errorFunction, successFunction)
  }

  def delete(orderId: String) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    orderRepository.delete(orderId).map(_ => Redirect(routes.OrderController.getAll()).flashing("info" -> "Zamówienie usunięte!"))
  }

}

case class CreateOrderForm(user: String,
                           payment: String,
                           movies: Seq[String]
                          )
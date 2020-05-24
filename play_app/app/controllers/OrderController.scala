package controllers

import javax.inject.{Inject, Singleton}
import models.{Movie, Order, OrderItem, Payment, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.{MovieRepository, OrderRepository, PaymentRepository, UserRepository}

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
    val orderItems: Seq[(OrderItem, Movie)] = Await.result(orderRepository.getOrderItemsWithMovie(orderId), Duration.Inf)
    val value: Double = Await.result(orderRepository.getOrderValue(orderId), Duration.Inf)

    orderRepository.getByIdWithUserAndPayment(orderId) map {
      case Some(o) => Ok(views.html.order.order(o, orderItems, value))
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


  def update(orderId: String) = Action { implicit request: MessagesRequest[AnyContent] =>
    val order: Order = Await.result(orderRepository.getById(orderId), Duration.Inf).get

    val users: Seq[User] = Await.result(userRepository.getAll(), Duration.Inf)
    val payments: Seq[Payment] = Await.result(paymentRepository.getAll(), Duration.Inf)

    val movies: Seq[Movie] = Await.result(movieRepository.getAll(), Duration.Inf)
    val selectedMovies: Seq[String] = Await.result(orderRepository.getMoviesForOrder(orderId), Duration.Inf).map(_.id)

    val updateForm = createOrderForm.fill(CreateOrderForm(order.user, order.payment, selectedMovies))

    Ok(views.html.order.update_order(orderId, updateForm, users, payments, selectedMovies, movies))
  }

  def updateOrderHandler(orderId: String): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[CreateOrderForm] =>
      Future {
        Redirect(routes.OrderController.update(orderId)).flashing("error" -> "Błąd podczas edycji zamówienia!")
      }
    }

    val successFunction = { order: CreateOrderForm =>
      orderRepository.update(orderId, order.user, order.payment, order.movies).map { _ =>
        Redirect(routes.OrderController.getAll()).flashing("success" -> "Zamówienie zmodyfikowane!")
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
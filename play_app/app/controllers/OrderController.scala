package controllers

import javax.inject.{Inject, Singleton}
import models.{Comment, Movie, Order, OrderItem, User}
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repositories.{OrderRepository, UserRepository}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class OrderController @Inject()(orderRepository: OrderRepository, userRepository: UserRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

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

  def create = Action {
    Ok("")
  }

  def update(actorId: String) = Action {
    Ok("")
  }

  def delete(actorId: String) = Action {
    Ok("")
  }


}

package controllers.api

import javax.inject.{Inject, Singleton}
import models.{Order, Payment, User}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

case class CreateOrder(user: String,
                       payment: String,
                       movies: Seq[String])

object CreateOrder {
  implicit val orderFormat = Json.format[CreateOrder]
}


case class UpdateOrder(user: Option[String],
                       payment: Option[String],
                       movies: Option[Seq[String]])

object UpdateOrder {
  implicit val orderFormat = Json.format[UpdateOrder]
}

@Singleton
class OrderApiController @Inject()(orderRepository: OrderRepository,
                                   movieRepository: MovieRepository,
                                   userRepository: UserRepository,
                                   paymentRepository: PaymentRepository,
                                   cc: MessagesControllerComponents
                                  )(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val orders: Future[Seq[(Order, Payment, User, Double)]] = orderRepository.getAllWithPaymentAndUser()
    orders.map(orders => {
      val newOrder = orders.map {
        case (o, p, u, v) => Json.obj("id" -> o.id, "payment" -> p, "user" -> u, "value" -> v)
      }
      Ok(Json.toJson(newOrder))
    })
  }

  def get(id: String) = Action.async { implicit request =>
    orderRepository.getByIdWithUserAndPayment(id) map {
      case Some(order) => {
        val orderItems = Await.result(orderRepository.getOrderItemsWithMovie(id), Duration.Inf)
        val orderItemsJson = orderItems.map {
          case (oi, m) => Json.obj("price" -> oi.price, "movie" -> m)
        }
        val value: Double = Await.result(orderRepository.getOrderValue(id), Duration.Inf)
        Ok(Json.obj("id" -> order._1.id, "payment" -> order._2, "user" -> order._3, "value" -> value, "items" -> orderItemsJson))
      }
      case None => NotFound(Json.obj("message" -> "Order does not exist"))
    }
  }

  def create() = Action(parse.json) { request =>
    val body = request.body

    body.validate[CreateOrder].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      order => {
        var invalidMovies: Seq[String] = Seq()
        for (m <- order.movies) {
          if (!(Await.result(movieRepository.isExist(m), Duration.Inf))) {
            invalidMovies = invalidMovies :+ m
          }
        }
        val isUserExist: Boolean = Await.result(userRepository.isExist(order.user), Duration.Inf)
        val isPaymentExist: Boolean = Await.result(paymentRepository.isExist(order.payment), Duration.Inf)

        val valid: Boolean = invalidMovies.isEmpty && isUserExist && isPaymentExist
        if (!valid) {
          if (!isUserExist) {
            BadRequest(Json.obj("message" -> "User does not exist"))
          } else if (!isPaymentExist) {
            BadRequest(Json.obj("message" -> "Payment does not exist"))
          } else {
            BadRequest(Json.obj("invalid_movies" -> invalidMovies))
          }
        } else {
          orderRepository.create(order.user, order.payment, order.movies)
          Ok(Json.obj("message" -> "Order created"))
        }
      }
    )
  }


  def update(id: String) = Action.async(parse.json) { implicit request =>
    orderRepository.getById(id) map {
      case Some(o) => {
        val body = request.body
        body.validate[UpdateOrder].fold(
          errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
          },
          order => {
            var invalidMovies: Seq[String] = Seq()
            if (order.movies.nonEmpty) {
              for (m <- order.movies.get) {
                if (!(Await.result(movieRepository.isExist(m), Duration.Inf))) {
                  invalidMovies = invalidMovies :+ m
                }
              }
            }

            val isUserExist: Boolean = Await.result(userRepository.isExist(order.user.getOrElse(o.user)), Duration.Inf)
            val isPaymentExist: Boolean = Await.result(paymentRepository.isExist(order.payment.getOrElse(o.payment)), Duration.Inf)

            val valid: Boolean = invalidMovies.isEmpty && isUserExist && isPaymentExist

            if (!valid) {
              if (!isUserExist) {
                BadRequest(Json.obj("message" -> "User does not exist"))
              } else if (!isPaymentExist) {
                BadRequest(Json.obj("message" -> "Payment does not exist"))
              } else {
                BadRequest(Json.obj("invalid_movies" -> invalidMovies))
              }
            } else {
              val currentMovies: Seq[String] = Await.result(orderRepository.getMoviesForOrder(id), Duration.Inf).map(_.id)

              orderRepository.update(id, order.user.getOrElse(o.user), order.payment.getOrElse(o.payment), order.movies.getOrElse(currentMovies))
              Ok(Json.obj("message" -> "Order updated"))
            }
          }
        )
      }
      case None => NotFound(Json.obj("message" -> "Order does not exist"))
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    orderRepository.getById(id) map {
      case Some(o) => {
        orderRepository.delete(id)
        Ok(Json.obj("message" -> "Order deleted"))
      }
      case None => NotFound(Json.obj("message" -> "Order does not exist"))
    }
  }
}

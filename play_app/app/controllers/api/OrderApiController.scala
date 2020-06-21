package controllers.api

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.{Inject, Singleton}
import models.auth.{User, UserRoles}
import models.{Order, Payment}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repositories._
import repositories.auth.UserService
import utils.auth.{JsonErrorHandler, JwtEnv, RoleJWTAuthorization}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

case class CreateOrder(payment: String,
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
                                   userRepository: UserService,
                                   paymentRepository: PaymentRepository,
                                   cc: MessagesControllerComponents,
                                   errorHandler: JsonErrorHandler,
                                   silhouette: Silhouette[JwtEnv]
                                  )(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

 val orderNotFound = NotFound(Json.obj("message" -> "Order does not exist"))

  def getAll: Action[AnyContent] = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async { implicit request =>
    val orders: Future[Seq[(Order, Payment, User, Double)]] = orderRepository.getAllWithPaymentAndUser()
    orders.map(orders => {
      val newOrder = orders.map {
        case (o, p, u, v) => Json.obj("id" -> o.id, "payment" -> p, "user" -> u, "value" -> v)
      }
      Ok(Json.toJson(newOrder))
    })
  }

  def get(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async { implicit request =>
    orderRepository.getByIdWithUserAndPayment(id) map {
      case Some(order) => {
        val orderItems = Await.result(orderRepository.getOrderItemsWithMovie(id), Duration.Inf)
        val orderItemsJson = orderItems.map {
          case (oi, m) => Json.obj("price" -> oi.price, "movie" -> m)
        }
        val value: Double = Await.result(orderRepository.getOrderValue(id), Duration.Inf)
        Ok(Json.obj("id" -> order._1.id, "payment" -> order._2, "user" -> order._3, "value" -> value, "items" -> orderItemsJson))
      }
      case None => orderNotFound
    }
  }

  def create() = silhouette.SecuredAction(errorHandler).async(parse.json) { request =>
    val body = request.body

    body.validate[CreateOrder].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      order => {
        var invalidMovies: Seq[String] = Seq()
        for (m <- order.movies) {
          if (!(Await.result(movieRepository.isExist(m), Duration.Inf))) {
            invalidMovies = invalidMovies :+ m
          }
        }
        val isUserExist: Boolean = Await.result(userRepository.isExist(request.identity.id), Duration.Inf)
        val isPaymentExist: Boolean = Await.result(paymentRepository.isExist(order.payment), Duration.Inf)

        val valid: Boolean = invalidMovies.isEmpty && isUserExist && isPaymentExist
        if (!valid) {
          if (!isUserExist) {
            Future.successful(BadRequest(Json.obj("message" -> "User does not exist")))
          } else if (!isPaymentExist) {
            Future.successful(BadRequest(Json.obj("message" -> "Payment does not exist")))
          } else {
            Future.successful(BadRequest(Json.obj("invalid_movies" -> invalidMovies)))
          }
        } else {
          orderRepository.create(request.identity.id, order.payment, order.movies)
          Future.successful(Ok(Json.obj("message" -> "Order created")))
        }
      }
    )
  }


  def update(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async(parse.json) { implicit request =>
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
      case None => orderNotFound
    }
  }

  def delete(id: String) = silhouette.SecuredAction(RoleJWTAuthorization(UserRoles.Admin)).async { implicit request =>
    orderRepository.getById(id) map {
      case Some(o) => {
        orderRepository.delete(id)
        Ok(Json.obj("message" -> "Order deleted"))
      }
      case None => orderNotFound
    }
  }
}

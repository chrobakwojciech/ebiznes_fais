package repositories

import java.util.UUID

import javax.inject.{Inject, Singleton}
import models._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class OrderRepository @Inject()(movieRepository: MovieRepository, dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _order = TableQuery[OrderTable]
  val _payment = TableQuery[PaymentTable]
  val _user = TableQuery[UserTable]
  val _orderItems = TableQuery[OrderItemTable]
  val _movie = TableQuery[MovieTable]

  def getAllWithPaymentAndUser(): Future[Seq[(Order, Payment, User, Double)]] = db.run {
    _order.join(_payment).on(_.payment === _.id).join(_user).on(_._1.user === _.id).map {
      case ((order, payment), user) => {
        var value = 0.0
//        value = _orderItems.filter(_.order === order.id).result
        (order, payment, user, value)
      }
    }.result
  }

  def getOrderItemsWithMovie(orderId: String): Future[Seq[(OrderItem, Movie)]] = db.run {
    _orderItems.filter(_.order === orderId).join(_movie).on(_.movie === _.id).map {
      case (oi, m) => (oi, m)
    }.result
  }

  def getByIdWithUserAndPayment(orderId: String): Future[Option[(Order, Payment, User)]] = db.run {
    _order.filter(_.id === orderId).join(_payment).on(_.payment === _.id).join(_user).on(_._1.user === _.id).map {
      case ((order, payment), user) => (order, payment, user)
    }.result.headOption
  }

  def create(user: String, payment: String, movies: Seq[String]) = {
    val id: String = UUID.randomUUID().toString()
    val createOrder = _order.insertOrUpdate(Order(id, user, payment))

    var orderItems: Seq[OrderItem] = Seq()
    for (m <- movies) {
      val movie: Option[Movie] = Await.result(movieRepository.getById(m), Duration.Inf)
      orderItems = orderItems :+ OrderItem(id, movie.get.id, movie.get.price)
    }
    val createOrderItems = _orderItems ++= orderItems

    db.run(DBIO.seq(createOrder, createOrderItems).transactionally)
  }

  def delete(orderId: String) = {
    val deleteOrder = _order.filter(_.id === orderId).delete
    val deleteOrderItems = _orderItems.filter(_.order === orderId).delete
    db.run(DBIO.seq(deleteOrder, deleteOrderItems).transactionally)
  }

}

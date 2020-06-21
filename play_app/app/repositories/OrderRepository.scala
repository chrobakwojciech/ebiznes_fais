package repositories

import java.util.UUID

import javax.inject.{Inject, Singleton}
import models._
import models.auth.{User, UserTable}
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
        val value = _orderItems.filter(_.order === order.id).map(_.price).sum.getOrElse(0.0)
        (order, payment, user, value)
      }
    }.result
  }

  def getForUser(userId: String) = db.run {
    _order
      .filter(_.user === userId)
      .join(_payment).on(_.payment === _.id)
      .result
  }

  def getById(orderId: String): Future[Option[Order]] = db.run {
    _order.filter(_.id === orderId).result.headOption
  }

  def getOrderItemsWithMovie(orderId: String): Future[Seq[(OrderItem, Movie)]] = db.run {
    _orderItems.filter(_.order === orderId).join(_movie).on(_.movie === _.id).map {
      case (oi, m) => (oi, m)
    }.result
  }

  def getOrderValue(orderId: String): Future[Double] = db.run {
    _orderItems.filter(_.order === orderId).map(_.price).sum.result.map(_.getOrElse(0.0))
  }

  def getOrderItemsForOrder(orderId: String): Future[Seq[OrderItem]] = db.run {
    _orderItems.filter(_.order === orderId).result
  }

  def getMoviesForOrder(orderId: String): Future[Seq[Movie]] = db.run {
    _orderItems.filter(_.order === orderId).join(_movie).on(_.movie === _.id).map {
      case (oi, m) => (m)
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

  def update(orderId: String, user: String, payment: String, movies: Seq[String]) = {
    val updateOrder = _order.filter(_.id === orderId).update(Order(orderId, user, payment))

    var orderItems: Seq[OrderItem] = Seq()
    for (m <- movies) {
      val movie: Option[Movie] = Await.result(movieRepository.getById(m), Duration.Inf)
      orderItems = orderItems :+ OrderItem(orderId, movie.get.id, movie.get.price)
    }
    val cleanOrderItems = _orderItems.filter(_.order === orderId).delete
    val createOrderItems = _orderItems ++= orderItems

    db.run(DBIO.seq(updateOrder, cleanOrderItems, createOrderItems).transactionally)
  }

}

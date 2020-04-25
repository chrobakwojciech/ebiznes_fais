package repositories

import javax.inject.{Inject, Singleton}
import models.{Movie, MovieTable, Order, OrderItem, OrderItemTable, OrderTable, Payment, PaymentTable, User, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
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

}

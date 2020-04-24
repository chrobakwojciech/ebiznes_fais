package repositories

import javax.inject.{Inject, Singleton}
import models.{Order, OrderTable, Payment, PaymentTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository, paymentRepository: PaymentRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _order = TableQuery[OrderTable]

  def getAll(): Future[Seq[Order]] = db.run {
    _order.result
  }

  def getById(orderId: String): Future[Option[Order]] = db.run {
    _order.filter(_.id === orderId).result.headOption
  }

}

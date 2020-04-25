package repositories

import javax.inject.{Inject, Singleton}
import models.{Order, OrderTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _order = TableQuery[OrderTable]

  def getById(orderId: String): Future[Option[Order]] = db.run {
    _order.filter(_.id === orderId).result.headOption
  }

}

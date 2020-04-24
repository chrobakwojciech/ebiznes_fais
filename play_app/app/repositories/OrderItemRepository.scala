package repositories

import javax.inject.{Inject, Singleton}
import models.{OrderItem, OrderItemTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderItemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, movieRepository: MovieRepository, orderRepository: OrderRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  val _orderItem = TableQuery[OrderItemTable]

  def getAll(): Future[Seq[OrderItem]] = db.run {
    _orderItem.result
  }

  def getById(orderItemId: String): Future[Option[OrderItem]] = db.run {
    _orderItem.filter(_.id === orderItemId).result.headOption
  }

}

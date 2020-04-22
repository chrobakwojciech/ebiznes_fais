package repositories

import javax.inject.{Inject, Singleton}
import models.OrderItemTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class OrderItemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, movieRepository: MovieRepository, orderRepository: OrderRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  val _orderItem = TableQuery[OrderItemTable]

  //
  // methods
  //

}

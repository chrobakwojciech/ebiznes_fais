package repositories

import javax.inject.{Inject, Singleton}
import models.OrderItem
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class OrderItemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, movieRepository: MovieRepository, orderRepository: OrderRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "orderItem") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def order = column[Long]("order")

    def order_fk = foreignKey("order_fk", order, _order)(_.id)

    def movie = column[Long]("movie")

    def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

    def * = (id, order, movie) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
  }

  import movieRepository.MovieTable
  import orderRepository.OrderTable

  private val _orderItem = TableQuery[OrderItemTable]
  private val _movie = TableQuery[MovieTable]
  private val _order = TableQuery[OrderTable]

  //
  // methods
  //

}

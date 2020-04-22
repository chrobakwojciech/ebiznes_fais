package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class OrderItem(id: Long,
                     order: Long,
                     movie: Long
                    )

class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "orderItem") {
  val _movie = TableQuery[MovieTable]
  val _order = TableQuery[OrderTable]

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def order = column[Long]("order")

  def order_fk = foreignKey("order_fk", order, _order)(_.id)

  def movie = column[Long]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

  def * = (id, order, movie) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
}


object OrderItem {
  implicit val orderItemFormat = Json.format[OrderItem]
}

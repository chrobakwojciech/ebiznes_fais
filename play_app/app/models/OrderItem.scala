package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class OrderItem(order: String,
                     movie: String,
                     price: Double
                    )

class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "orderItem") {
  val _movie = TableQuery[MovieTable]
  val _order = TableQuery[OrderTable]

  def order = column[String]("order")

  def order_fk = foreignKey("order_fk", order, _order)(_.id)

  def movie = column[String]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

  def price = column[Double]("price")

  def pk = primaryKey("primaryKey", (order, movie))

  def * = (order, movie, price) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
}


object OrderItem {
  implicit val orderItemFormat = Json.format[OrderItem]
}

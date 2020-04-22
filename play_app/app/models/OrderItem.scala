package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class OrderItem(id: String,
                     order: String,
                     movie: String
                    )

class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "orderItem") {
  val _movie = TableQuery[MovieTable]
  val _order = TableQuery[OrderTable]

  def id = column[String]("id", O.PrimaryKey)

  def order = column[String]("order")

  def order_fk = foreignKey("order_fk", order, _order)(_.id)

  def movie = column[String]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

  def * = (id, order, movie) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
}


object OrderItem {
  implicit val orderItemFormat = Json.format[OrderItem]
}

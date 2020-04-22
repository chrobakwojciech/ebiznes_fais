package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Order(id: Long,
                 user: Long,
                 payment: Long
                )

class OrderTable(tag: Tag) extends Table[Order](tag, "order") {
  val _payment = TableQuery[PaymentTable]
  val _user = TableQuery[UserTable]

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def user = column[Long]("user")

  def user_fk = foreignKey("user_fk", user, _user)(_.id)

  def payment = column[Long]("payment")

  def payment_fk = foreignKey("payment_fk", payment, _payment)(_.id)

  def * = (id, user, payment) <> ((Order.apply _).tupled, Order.unapply)
}

object Order {
  implicit val orderFormat = Json.format[Order]
}

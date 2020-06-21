package models

import models.auth.UserTable
import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Order(id: String,
                 user: String,
                 payment: String
                )

class OrderTable(tag: Tag) extends Table[Order](tag, "order") {
  val _payment = TableQuery[PaymentTable]
  val _user = TableQuery[UserTable]

  def id = column[String]("id", O.PrimaryKey)

  def user = column[String]("user")

  def user_fk = foreignKey("user_fk", user, _user)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def payment = column[String]("payment")

  def payment_fk = foreignKey("payment_fk", payment, _payment)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def * = (id, user, payment) <> ((Order.apply _).tupled, Order.unapply)
}

object Order {
  implicit val orderFormat = Json.format[Order]
}

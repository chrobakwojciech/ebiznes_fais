package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Payment(id: String,
                   name: String
                  )

class PaymentTable(tag: Tag) extends Table[Payment](tag, "payment") {
  def id = column[String]("id", O.PrimaryKey)

  def name = column[String]("name")

  def * = (id, name) <> ((Payment.apply _).tupled, Payment.unapply)
}


object Payment {
  implicit val paymentFormat = Json.format[Payment]
}

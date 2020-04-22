package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Payment(id: Long,
                   name: String,
                   price: Double
                  )

class PaymentTable(tag: Tag) extends Table[Payment](tag, "payment") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def price = column[Double]("price")

  def * = (id, name, price) <> ((Payment.apply _).tupled, Payment.unapply)
}


object Payment {
  implicit val paymentFormat = Json.format[Payment]
}

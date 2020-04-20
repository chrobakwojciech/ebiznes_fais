package models

import play.api.libs.json.Json

case class Payment(id: Long,
                   name: String,
                   price: Double
                  )

object Payment {
  implicit val paymentFormat = Json.format[Payment]
}

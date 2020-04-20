package models

import play.api.libs.json.Json

case class OrderItem(id: Long,
                     orderId: Long,
                     movieId: Long
                    )

object OrderItem {
  implicit val orderItemFormat = Json.format[OrderItem]
}

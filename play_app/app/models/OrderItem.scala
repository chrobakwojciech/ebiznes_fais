package models

import play.api.libs.json.Json

case class OrderItem(id: Long,
                     order: Long,
                     movie: Long
                    )

object OrderItem {
  implicit val orderItemFormat = Json.format[OrderItem]
}

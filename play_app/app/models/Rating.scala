package models

import play.api.libs.json.Json

case class Rating(id: Long,
                  value: Int,
                  user: Long,
                  movie: Long
                 )

object Rating {
  implicit val ratingFormat = Json.format[Rating]
}

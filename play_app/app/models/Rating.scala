package models

import play.api.libs.json.Json

case class Rating(id: Long,
                  value: Int,
                  userId: Long,
                  movieId: Long
                 )

object Rating {
  implicit val ratingFormat = Json.format[Rating]
}

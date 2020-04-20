package models

import play.api.libs.json.Json

case class Genre(id: Long,
                 name: String
                )

object Genre {
  implicit val genreFormat = Json.format[Genre]
}

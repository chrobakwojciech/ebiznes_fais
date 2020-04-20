package models

import play.api.libs.json.Json

case class Movie(id: Long,
                 title: String,
                 description: String,
                 productionYear: String,
                 img: String
                )

object Movie {
  implicit val movieFormat = Json.format[Movie]
}

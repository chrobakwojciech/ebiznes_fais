package models

import java.util.Date

import play.api.libs.json.Json

case class Movie(id: Long,
                 title: String,
                 description: String,
                 productionYear: Date,
                 img: String
                )

object Movie {
  implicit val movieFormat = Json.format[Movie]
}

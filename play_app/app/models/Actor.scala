package models

import play.api.libs.json.Json

case class Actor(id: Long,
                 firstName: String,
                 lastName: String,
                 img: String
                )

object Actor {
  implicit val actorFormat = Json.format[Actor]
}

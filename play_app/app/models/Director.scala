package models

import play.api.libs.json.Json

case class Director(id: Long,
                    firstName: String,
                    lastName: String,
                    img: String
                   )

object Director {
  implicit val directorFormat = Json.format[Director]
}

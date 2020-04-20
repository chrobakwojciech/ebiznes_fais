package models

import play.api.libs.json.Json

case class Comment(id: Long,
                   content: String,
                   userId: Long,
                   movieId: Long
                  )

object Comment {
  implicit val commentFormat = Json.format[Comment]
}

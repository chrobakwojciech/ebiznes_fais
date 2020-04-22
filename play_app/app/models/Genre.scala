package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Genre(id: Long,
                 name: String
                )

class GenreTable(tag: Tag) extends Table[Genre](tag, "genre") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def * = (id, name) <> ((Genre.apply _).tupled, Genre.unapply)
}


object Genre {
  implicit val genreFormat = Json.format[Genre]
}

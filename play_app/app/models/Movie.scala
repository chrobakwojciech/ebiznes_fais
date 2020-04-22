package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Movie(id: String,
                 title: String,
                 description: String,
                 productionYear: String,
                 img: String
                )

class MovieTable(tag: Tag) extends Table[Movie](tag, "movie") {
  def id = column[String]("id", O.PrimaryKey)

  def title = column[String]("title")

  def description = column[String]("description")

  def productionYear = column[String]("productionYear")

  def img = column[String]("img")

  def * = (id, title, description, productionYear, img) <> ((Movie.apply _).tupled, Movie.unapply)
}


object Movie {
  implicit val movieFormat = Json.format[Movie]
}

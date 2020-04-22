package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Director(id: String,
                    firstName: String,
                    lastName: String,
                    img: String
                   )

class DirectorTable(tag: Tag) extends Table[Director](tag, "director") {
  def id = column[String]("id", O.PrimaryKey)

  def firstName = column[String]("firstName")

  def lastName = column[String]("lastName")

  def img = column[String]("img")

  def * = (id, firstName, lastName, img) <> ((Director.apply _).tupled, Director.unapply)
}


object Director {
  implicit val directorFormat = Json.format[Director]
}

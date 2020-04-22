package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Actor(id: Long,
                 firstName: String,
                 lastName: String,
                 img: String
                )

class ActorTable(tag: Tag) extends Table[Actor](tag, "actor") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def firstName = column[String]("firstName")

  def lastName = column[String]("lastName")

  def img = column[String]("img")

  def * = (id, firstName, lastName, img) <> ((Actor.apply _).tupled, Actor.unapply)
}


object Actor {
  implicit val actorFormat = Json.format[Actor]
}

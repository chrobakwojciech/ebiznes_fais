package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class User(id: String,
                firstName: String,
                lastName: String,
                email: String,
                password: String
               )

class UserTable(tag: Tag) extends Table[User](tag, "user") {
  def id = column[String]("id", O.PrimaryKey)

  def firstName = column[String]("firstName")

  def lastName = column[String]("lastName")

  def email = column[String]("email")

  def password = column[String]("password")

  def * = (id, firstName, lastName, email, password) <> ((User.apply _).tupled, User.unapply)
}

object User {
  implicit val userFormat = Json.format[User]
}

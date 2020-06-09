package models

import com.mohiva.play.silhouette.api.Identity
import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

object UserRoles extends Enumeration {
  type UserRole = String
  val User = "user"
  val Admin = "admin"
}

case class User(id: String,
                firstName: String,
                lastName: String,
                email: String,
                role: UserRoles.UserRole,
                providerId: String,
                providerKey: String) extends Identity

class UserTable(tag: Tag) extends Table[User](tag, "user") {
  def id = column[String]("id", O.PrimaryKey)

  def firstName = column[String]("firstName")

  def lastName = column[String]("lastName")

  def email = column[String]("email")

  def role = column[String]("role")

  def providerId = column[String]("providerId")

  def providerKey = column[String]("providerKey")

  def * = (id, firstName, lastName, email, role, providerId, providerKey) <> ((User.apply _).tupled, User.unapply)
}

object User {
  implicit val userFormat = Json.format[User]
}

case class Password(key: String,
                    hasher: String,
                    hash: String,
                    salt: Option[String])


class PasswordTable(tag: Tag) extends Table[Password](tag, "password") {
  def key = column[String]("providerKey", O.PrimaryKey)

  def hasher = column[String]("hasher")

  def hash = column[String]("hash")

  def salt = column[Option[String]]("salt")

  def * = (key, hasher, hash, salt) <> ((Password.apply _).tupled, Password.unapply)
}

object Password {
  implicit val passwordFormat = Json.format[Password]
}
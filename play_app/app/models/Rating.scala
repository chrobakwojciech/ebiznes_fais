package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Rating(id: String,
                  value: Int,
                  user: String,
                  movie: String
                 )

class RatingTable(tag: Tag) extends Table[Rating](tag, "rating") {
  val _user = TableQuery[UserTable]
  val _movie = TableQuery[MovieTable]

  def id = column[String]("id", O.PrimaryKey)

  def value = column[Int]("value")

  def user = column[String]("user")

  def user_fk = foreignKey("user_fk", user, _user)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def movie = column[String]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def * = (id, value, user, movie) <> ((Rating.apply _).tupled, Rating.unapply)
}


object Rating {
  implicit val ratingFormat = Json.format[Rating]
}

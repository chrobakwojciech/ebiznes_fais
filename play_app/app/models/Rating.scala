package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Rating(id: Long,
                  value: Int,
                  user: Long,
                  movie: Long
                 )

class RatingTable(tag: Tag) extends Table[Rating](tag, "rating") {
  val _user = TableQuery[UserTable]
  val _movie = TableQuery[MovieTable]

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def value = column[Int]("value")

  def user = column[Long]("user")

  def user_fk = foreignKey("user_fk", user, _user)(_.id)

  def movie = column[Long]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

  def * = (id, value, user, movie) <> ((Rating.apply _).tupled, Rating.unapply)
}


object Rating {
  implicit val ratingFormat = Json.format[Rating]
}

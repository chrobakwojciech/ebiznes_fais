package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Comment(id: Long,
                   content: String,
                   user: Long,
                   movie: Long
                  )

class CommentTable(tag: Tag) extends Table[Comment](tag, "comment") {
  val _user = TableQuery[UserTable]
  val _movie = TableQuery[MovieTable]


  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def content = column[String]("content")

  def user = column[Long]("user")

  def user_fk = foreignKey("user_fk", user, _user)(_.id)

  def movie = column[Long]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

  def * = (id, content, user, movie) <> ((Comment.apply _).tupled, Comment.unapply)
}

object Comment {
  implicit val commentFormat = Json.format[Comment]
}

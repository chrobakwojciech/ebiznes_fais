package models

import models.auth.UserTable
import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class Comment(id: String,
                   content: String,
                   user: String,
                   movie: String
                  )

class CommentTable(tag: Tag) extends Table[Comment](tag, "comment") {
  val _user = TableQuery[UserTable]
  val _movie = TableQuery[MovieTable]


  def id = column[String]("id", O.PrimaryKey)

  def content = column[String]("content")

  def user = column[String]("user")

  def user_fk = foreignKey("user_fk", user, _user)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def movie = column[String]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def * = (id, content, user, movie) <> ((Comment.apply _).tupled, Comment.unapply)
}

object Comment {
  implicit val commentFormat = Json.format[Comment]
}

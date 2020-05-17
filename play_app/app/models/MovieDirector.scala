package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class MovieDirector(movie: String,
                         director: String
                        )

class MovieDirectorTable(tag: Tag) extends Table[MovieDirector](tag, "movie_director") {

  val _movie = TableQuery[MovieTable]
  val _director = TableQuery[DirectorTable]

  def movie = column[String]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def director = column[String]("director")

  def director_fk = foreignKey("director_fk", director, _director)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def pk = primaryKey("primaryKey", (movie, director))

  def * = (movie, director) <> ((MovieDirector.apply _).tupled, MovieDirector.unapply)
}


object MovieDirector {
  implicit val movieDirectorFormat = Json.format[MovieDirector]
}

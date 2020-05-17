package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class MovieGenre(movie: String,
                      genre: String
                     )

class MovieGenreTable(tag: Tag) extends Table[MovieGenre](tag, "movie_genre") {

  val _movie = TableQuery[MovieTable]
  val _genre = TableQuery[GenreTable]

  def movie = column[String]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def genre = column[String]("genre")

  def genre_fk = foreignKey("genre_fk", genre, _genre)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def pk = primaryKey("primaryKey", (movie, genre))

  def * = (movie, genre) <> ((MovieGenre.apply _).tupled, MovieGenre.unapply)
}


object MovieGenre {
  implicit val movieGenreFormat = Json.format[MovieGenre]
}

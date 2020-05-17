package models

import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

case class MovieActor(movie: String,
                      actor: String
                     )

class MovieActorTable(tag: Tag) extends Table[MovieActor](tag, "movie_actor") {

  val _movie = TableQuery[MovieTable]
  val _actor = TableQuery[ActorTable]

  def movie = column[String]("movie")

  def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def actor = column[String]("actor")

  def actor_fk = foreignKey("actor_fk", actor, _actor)(_.id, onUpdate = ForeignKeyAction.NoAction , onDelete = ForeignKeyAction.Cascade)

  def pk = primaryKey("primaryKey", (movie, actor))

  def * = (movie, actor) <> ((MovieActor.apply _).tupled, MovieActor.unapply)
}


object MovieActor {
  implicit val movieActorFormat = Json.format[MovieActor]
}

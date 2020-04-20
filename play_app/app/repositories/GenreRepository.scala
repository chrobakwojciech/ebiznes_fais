package repositories

import javax.inject.{Inject, Singleton}
import models.Genre
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class GenreRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class GenreTable(tag: Tag) extends Table[Genre](tag, "genre") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name) <> ((Genre.apply _).tupled, Genre.unapply)
  }

  private val _genre = TableQuery[GenreTable]

  //
  // methods
  //

}

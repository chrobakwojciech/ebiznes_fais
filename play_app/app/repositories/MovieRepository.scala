package repositories

import javax.inject.{Inject, Singleton}
import models.Movie
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class MovieRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class MovieTable(tag: Tag) extends Table[Movie](tag, "movie") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def description = column[String]("description")

    def productionYear = column[String]("productionYear")

    def img = column[String]("img")

    def * = (id, title, description, productionYear, img) <> ((Movie.apply _).tupled, Movie.unapply)
  }

  private val _movie = TableQuery[MovieTable]

  //
  // methods
  //

}

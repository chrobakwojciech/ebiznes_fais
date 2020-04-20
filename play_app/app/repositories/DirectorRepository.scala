package repositories

import javax.inject.{Inject, Singleton}
import models.Director
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class DirectorRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class DirectorTable(tag: Tag) extends Table[Director](tag, "director") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("firstName")

    def lastName = column[String]("lastName")

    def img = column[String]("img")

    def * = (id, firstName, lastName, img) <> ((Director.apply _).tupled, Director.unapply)
  }

  private val _director = TableQuery[DirectorTable]

  //
  // methods
  //

}

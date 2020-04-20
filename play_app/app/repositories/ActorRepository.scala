package repositories

import javax.inject.{Inject, Singleton}
import models.Actor
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class ActorRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class ActorTable(tag: Tag) extends Table[Actor](tag, "actor") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("firstName")

    def lastName = column[String]("lastName")

    def img = column[String]("img")

    def * = (id, firstName, lastName, img) <> ((Actor.apply _).tupled, Actor.unapply)
  }

  private val _actor = TableQuery[ActorTable]

  //
  // methods
  //

}

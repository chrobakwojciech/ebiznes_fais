package repositories

import javax.inject.{Inject, Singleton}
import models.{Director, DirectorTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DirectorRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _director = TableQuery[DirectorTable]

  def getAll(): Future[Seq[Director]] = db.run {
    _director.result
  }

  def getById(directorId: String): Future[Option[Director]] = db.run {
    _director.filter(_.id === directorId).result.headOption
  }

}

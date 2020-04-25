package repositories

import javax.inject.{Inject, Singleton}
import models.{Director, DirectorTable, MovieDirectorTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DirectorRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _director = TableQuery[DirectorTable]
  val _movieDirectors = TableQuery[MovieDirectorTable]

  def getAll(): Future[Seq[Director]] = db.run {
    _director.result
  }

  def getById(directorId: String): Future[Option[Director]] = db.run {
    _director.filter(_.id === directorId).result.headOption
  }

  def getForMovie(movieId: String): Future[Seq[Director]] = db.run {
    _movieDirectors.filter(_.movie === movieId).join(_director).on(_.director === _.id).map {
      case (ma, d) => d
    }.result
  }

}

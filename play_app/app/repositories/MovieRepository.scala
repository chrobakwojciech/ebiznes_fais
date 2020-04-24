package repositories

import javax.inject.{Inject, Singleton}
import models.{Movie, MovieTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MovieRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _movie = TableQuery[MovieTable]

  def getAll(): Future[Seq[Movie]] = db.run {
    _movie.result
  }

  def getById(movieId: String): Future[Option[Movie]] = db.run {
    _movie.filter(_.id === movieId).result.headOption
  }

}

package repositories

import javax.inject.{Inject, Singleton}
import models.{Genre, GenreTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _genre = TableQuery[GenreTable]

  def getAll(): Future[Seq[Genre]] = db.run {
    _genre.result
  }

  def getById(genreId: String): Future[Option[Genre]] = db.run {
    _genre.filter(_.id === genreId).result.headOption
  }

}

package repositories

import javax.inject.{Inject, Singleton}
import models.{Genre, GenreTable, MovieGenreTable, MovieTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _genre = TableQuery[GenreTable]
  val _movieGenres = TableQuery[MovieGenreTable]

  def getAll(): Future[Seq[Genre]] = db.run {
    _genre.result
  }

  def getById(genreId: String): Future[Option[Genre]] = db.run {
    _genre.filter(_.id === genreId).result.headOption
  }

  def getForMovie(movieId: String): Future[Seq[Genre]] = db.run {
    _movieGenres.filter(_.movie === movieId).join(_genre).on(_.genre === _.id).map {
      case (ma, g) => g
    }.result
  }

}

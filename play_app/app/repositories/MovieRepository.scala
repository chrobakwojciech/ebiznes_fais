package repositories

import javax.inject.{Inject, Singleton}
import models._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MovieRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _movie = TableQuery[MovieTable]
  val _movieGenres = TableQuery[MovieGenreTable]
  val _movieActors = TableQuery[MovieActorTable]
  val _movieDiretors = TableQuery[MovieDirectorTable]

  def getAll(): Future[Seq[Movie]] = db.run {
    _movie.result
  }

  def getById(movieId: String): Future[Option[Movie]] = db.run {
    _movie.filter(_.id === movieId).result.headOption
  }

  def getForGenre(genreId: String): Future[Seq[Movie]] = db.run {
    _movieGenres.filter(_.genre === genreId).join(_movie).on(_.movie === _.id).map {
      case (mg, m) => m
    }.result
  }

  def getForActor(actorId: String): Future[Seq[Movie]] = db.run {
    _movieActors.filter(_.actor === actorId).join(_movie).on(_.movie === _.id).map {
      case (ma, b) => b
    }.result
  }

  def getForDirector(directorId: String): Future[Seq[Movie]] = db.run {
    _movieDiretors.filter(_.director === directorId).join(_movie).on(_.movie === _.id).map {
      case (md, m) => m
    }.result
  }

}

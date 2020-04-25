package repositories

import javax.inject.{Inject, Singleton}
import models.{Movie, MovieTable, Rating, RatingTable, User, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RatingRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _rating = TableQuery[RatingTable]
  val _user = TableQuery[UserTable]
  val _movie = TableQuery[MovieTable]

  def getAll(): Future[Seq[Rating]] = db.run {
    _rating.result
  }

  def getAllWithMovieAndUser(): Future[Seq[(Rating, Movie, User)]] = db.run {
    _rating.join(_user).on(_.user === _.id).join(_movie).on(_._1.movie === _.id).map {
      case ((rating, user), movie) => (rating, movie, user)
    }.result
  }

  def getById(ratingId: String): Future[Option[Rating]] = db.run {
    _rating.filter(_.id === ratingId).result.headOption
  }

  def getForMovie(movieId: String): Future[Seq[(Rating, User)]] = db.run {
    _rating.filter(_.movie === movieId).join(_user).on(_.user === _.id).result
  }

}

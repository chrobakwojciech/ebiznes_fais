package repositories

import java.util.UUID

import javax.inject.{Inject, Singleton}
import models._
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

  def getByIdWithMovieAndUser(ratingId: String): Future[Option[(Rating, Movie, User)]] = db.run {
    _rating.filter(_.id === ratingId).join(_movie).on(_.movie === _.id).join(_user).on(_._1.user === _.id).map {
      case ((rating, movie), user) => (rating, movie, user)
    }.result.headOption
  }

  def getForMovie(movieId: String): Future[Seq[(Rating, User)]] = db.run {
    _rating.filter(_.movie === movieId).join(_user).on(_.user === _.id).result
  }

  def create(value: Int, userId: String, movieId: String) = db.run {
    val newId: String = UUID.randomUUID().toString()
    _rating.filter(_.user === userId).filter(_.movie === movieId).result.headOption.flatMap {
      case Some(r) => _rating.insertOrUpdate(Rating(r.id, value, userId, movieId));
      case None => _rating.insertOrUpdate(Rating(newId, value, userId, movieId));
    }
  }

  def delete(ratingId: String) = db.run {
    _rating.filter(_.id === ratingId).delete
  }

  def update(ratingId: String, value: Int, userId: String, movieId: String) = db.run {
    _rating.filter(_.id === ratingId).update(Rating(ratingId, value, userId, movieId))
  }

}

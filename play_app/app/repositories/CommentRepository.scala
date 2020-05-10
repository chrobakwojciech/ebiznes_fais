package repositories

import java.util.UUID

import javax.inject.{Inject, Singleton}
import models._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository, movieRepository: MovieRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _comment = TableQuery[CommentTable]
  val _user = TableQuery[UserTable]
  val _movie = TableQuery[MovieTable]

  def getAll(): Future[Seq[Comment]] = db.run {
    _comment.result
  }

  def getAllWithMovieAndUser(): Future[Seq[(Comment, Movie, User)]] = db.run {
    _comment.join(_movie).on(_.movie === _.id).join(_user).on(_._1.user === _.id).map {
      case ((comment, movie), user) => (comment, movie, user)
    }.result
  }

  def getById(commentId: String): Future[Option[Comment]] = db.run {
    _comment.filter(_.id === commentId).result.headOption
  }

  def getForMovie(movieId: String): Future[Seq[(Comment, User)]] = db.run {
    _comment.filter(_.movie === movieId).join(_user).on(_.user === _.id).result
  }

  def create(content: String, userId: String, movieId: String) = db.run {
    val id: String = UUID.randomUUID().toString()
    _comment.insertOrUpdate(Comment(id, content, userId, movieId));
  }

  def delete(commentId: String) = db.run {
    _comment.filter(_.id === commentId).delete
  }

}

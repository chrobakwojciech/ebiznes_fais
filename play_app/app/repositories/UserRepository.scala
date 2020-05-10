package repositories

import java.util.UUID

import controllers.{CreateMovieForm, CreateRatingForm}
import javax.inject.{Inject, Singleton}
import models.{User, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _user = TableQuery[UserTable]

  def getAll(): Future[Seq[User]] = db.run {
    _user.result
  }

  def getById(userId: String): Future[Option[User]] = db.run {
    _user.filter(_.id === userId).result.headOption
  }

  def create(firstName: String, lastName: String, email: String, password: String): Future[Int] = db.run {
    val id: String = UUID.randomUUID().toString()
    _user.insertOrUpdate(User(id, firstName, lastName, email, password))
  }

  def delete(userId: String): Future[Int] = db.run {
    _user.filter(_.id === userId).delete
  }

}

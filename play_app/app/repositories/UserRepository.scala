package repositories

import java.util.UUID

import javax.inject.{Inject, Singleton}
import models.{User, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _user = TableQuery[UserTable]

  def create(firstName: String, lastName: String, email: String, password: String): Future[Int] = db.run {
    val id: String = UUID.randomUUID().toString()
    _user += User(id, firstName, lastName, email, password)
  }

  def getAll(): Future[Seq[User]] = db.run {
    _user.result
  }
}

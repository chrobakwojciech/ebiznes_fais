package repositories

import javax.inject.{Inject, Singleton}
import models.User
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("firstName")

    def lastName = column[String]("lastName")

    def email = column[String]("email")

    def password = column[String]("password")

    def * = (id, firstName, lastName, email, password) <> ((User.apply _).tupled, User.unapply)
  }

  private val _user = TableQuery[UserTable]

  //
  // methods
  //

}

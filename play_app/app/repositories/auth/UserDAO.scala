package repositories.auth
import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.{Inject, Singleton}
import models.auth.{LoginInfoTable, User, UserLoginInfoTable, UserRoles, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class UserDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _user = TableQuery[UserTable]
  val _loginInfo = TableQuery[LoginInfoTable]
  val _userLoginInfo = TableQuery[UserLoginInfoTable]

  def save(firstName: String, lastName: String, email: String) = db.run {
    val id: String = UUID.randomUUID().toString()
    _user.insertOrUpdate(User(id, firstName, lastName, email, UserRoles.User)).map(_ => id)
  }

  def update(id: String, firstName: String, lastName: String, email: String, role: UserRoles.UserRole) = db.run {
    _user.filter(_.id === id).update(User(id, firstName, lastName, email, role))
  }

  def find(loginInfo: LoginInfo) = {
    val findLoginInfoQuery = _loginInfo.filter(dbLoginInfo =>
      dbLoginInfo.providerId === loginInfo.providerID &&  dbLoginInfo.providerKey === loginInfo.providerKey)
    val query = for {
      dbLoginInfo <- findLoginInfoQuery
      dbUserLoginInfo <- _userLoginInfo.filter(_.loginInfoId === dbLoginInfo.id)
      dbUser <- _user.filter(_.id === dbUserLoginInfo.userId)
    } yield dbUser
    db.run(query.result.headOption).map { dbUserOption =>
      dbUserOption.map { user =>
        User(user.id, user.firstName, user.lastName, user.email, user.role)
      }
    }
  }

}
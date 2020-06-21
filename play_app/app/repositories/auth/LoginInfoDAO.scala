package repositories.auth
import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.{Inject, Singleton}
import models.auth.{LoginInfoDb, LoginInfoTable, User, UserLoginInfo, UserLoginInfoTable, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class LoginInfoDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _user = TableQuery[UserTable]
  val _loginInfo = TableQuery[LoginInfoTable]
  val _userLoginInfo = TableQuery[UserLoginInfoTable]

  def saveUserLoginInfo(userID: String, loginInfo: LoginInfo) = {
    val id = UUID.randomUUID().toString
    val dbLoginInfo = LoginInfoDb(id, loginInfo.providerID, loginInfo.providerKey)

    val actions = for {
      _ <- _loginInfo += dbLoginInfo
      userLoginInfo = UserLoginInfo(userID, dbLoginInfo.id)
      _ <- _userLoginInfo += userLoginInfo
    } yield ()
    db.run(actions)
  }

  def checkEmailIsAlreadyInUse(email: String) = db.run {
    _user.filter(_.email === email)
      .exists
      .result
  }

  def getAuthenticationProviders(email: String) = {
    val action = for {
      ((_, _), loginInfo) <- _user.filter(_.email === email)
        .join(_userLoginInfo).on(_.id === _.userId)
        .join(_loginInfo).on(_._2.loginInfoId === _.id)
    } yield loginInfo.providerId

    db.run(action.result)
  }
}
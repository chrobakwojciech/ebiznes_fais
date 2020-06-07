package repositories

import java.util.UUID

import com.mohiva.play.silhouette.api.{AuthInfo, LoginInfo, Provider}
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.util.{PasswordHasher, PasswordInfo}
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import javax.inject.{Inject, Singleton}
import models.{Password, PasswordTable, User, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag
import scala.util.{Failure, Success}

@Singleton
class PasswordDAO @Inject()(dbConfigProvider: DatabaseConfigProvider)
                           (implicit  val classTag: ClassTag[PasswordInfo], ec: ExecutionContext) extends DelegableAuthInfoDAO[PasswordInfo] {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _password = TableQuery[PasswordTable]

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = db.run {
    _password.filter(password => password.key === loginInfo.providerKey)
      .result
      .headOption
      .map(_.map(password => PasswordInfo(password.hasher, password.hash, password.salt)))
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    db.run {
      _password += Password(loginInfo.providerKey, authInfo.hasher, authInfo.password, authInfo.salt)
    }.map((res: Int) => {
      authInfo
    })
  }

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    val q = for {
      password <- _password if password.key === loginInfo.providerKey
    } yield (password.hasher, password.hash, password.salt)

    db.run(q.update(authInfo.hasher, authInfo.password, authInfo.salt)).map(_ => authInfo)
  }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    find(loginInfo).flatMap {
      case Some(_) => update(loginInfo, authInfo)
      case None => add(loginInfo, authInfo)
    }

  override def remove(loginInfo: LoginInfo): Future[Unit] = db.run(
    _password.filter(password => password.key === loginInfo.providerKey).delete
  ).map(i => Unit)

}

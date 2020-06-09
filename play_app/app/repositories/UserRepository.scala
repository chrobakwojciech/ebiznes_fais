package repositories

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.util.{PasswordHasher, PasswordInfo}
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.{Inject, Singleton}
import models.{PasswordTable, User, UserRoles, UserTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider,
                               passwordHasher: PasswordHasher,
                               authInfoRepository: AuthInfoRepository)
                              (implicit ec: ExecutionContext) extends IdentityService[User] {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _user = TableQuery[UserTable]
  val _password = TableQuery[PasswordTable]

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = db.run {
    _user.filter(user => user.providerKey === loginInfo.providerKey && user.providerId === loginInfo.providerID)
      .result
      .headOption
  }

  def create(firstName: String, lastName: String, email: String, password: String) = {
    val id: String = UUID.randomUUID().toString()
    db.run {
      _user.insertOrUpdate(User(id, firstName, lastName, email, UserRoles.User, CredentialsProvider.ID, email))
    } andThen {
      case Failure(_: Throwable) => None
      case Success(_) => {
        val loginInfo: LoginInfo = LoginInfo(CredentialsProvider.ID, email)
        val authInfo: PasswordInfo = passwordHasher.hash(password)
        authInfoRepository.add(loginInfo, authInfo)
      }
    } map { _id => LoginInfo(CredentialsProvider.ID, email) }
  }

  def getAll(): Future[Seq[User]] = db.run {
    _user.result
  }


  def isExist(userId: String): Future[Boolean] = db.run {
    _user.filter(_.id === userId).exists.result
  }

  def getById(userId: String): Future[Option[User]] = db.run {
    _user.filter(_.id === userId).result.headOption
  }

  def delete(userId: String): Future[Int] = db.run {
    val user: User = Await.result(getById(userId), Duration.Inf).get
    _password.filter(_.key === user.providerKey).delete
    _user.filter(_.id === userId).delete
  }

  def update(id: String, firstName: String, lastName: String, email: String, role: UserRoles.UserRole): Future[Int] = db.run {
    _user.filter(_.id === id).update(User(id, firstName, lastName, email, role, CredentialsProvider.ID, email))
  }

}

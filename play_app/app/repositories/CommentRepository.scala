package repositories

import javax.inject.{Inject, Singleton}
import models.{Comment, CommentTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository, movieRepository: MovieRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _comment = TableQuery[CommentTable]

  def getAll(): Future[Seq[Comment]] = db.run {
    _comment.result
  }

  def getById(commentId: String): Future[Option[Comment]] = db.run {
    _comment.filter(_.id === commentId).result.headOption
  }

}

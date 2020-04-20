package repositories

import javax.inject.{Inject, Singleton}
import models.Comment
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class CommentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository, movieRepository: MovieRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class CommentTable(tag: Tag) extends Table[Comment](tag, "comment") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def content = column[String]("content")

    def user = column[Long]("user")

    def user_fk = foreignKey("user_fk", user, _user)(_.id)

    def movie = column[Long]("movie")

    def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

    def * = (id, content, user, movie) <> ((Comment.apply _).tupled, Comment.unapply)
  }

  import movieRepository.MovieTable
  import userRepository.UserTable

  private val _comment = TableQuery[CommentTable]
  private val _user = TableQuery[UserTable]
  private val _movie = TableQuery[MovieTable]

  //
  // methods
  //

}

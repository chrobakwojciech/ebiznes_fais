package repositories

import javax.inject.{Inject, Singleton}
import models.Rating
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class RatingRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, movieRepository: MovieRepository, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class RatingTable(tag: Tag) extends Table[Rating](tag, "rating") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def value = column[Int]("value")

    def user = column[Long]("user")

    def user_fk = foreignKey("user_fk", user, _user)(_.id)

    def movie = column[Long]("movie")

    def movie_fk = foreignKey("movie_fk", movie, _movie)(_.id)

    def * = (id, value, user, movie) <> ((Rating.apply _).tupled, Rating.unapply)
  }

  import movieRepository.MovieTable
  import userRepository.UserTable

  private val _rating = TableQuery[RatingTable]
  private val _user = TableQuery[UserTable]
  private val _movie = TableQuery[MovieTable]

  //
  // methods
  //

}

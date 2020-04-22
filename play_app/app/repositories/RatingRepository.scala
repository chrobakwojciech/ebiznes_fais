package repositories

import javax.inject.{Inject, Singleton}
import models.RatingTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class RatingRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, movieRepository: MovieRepository, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  val _rating = TableQuery[RatingTable]

  //
  // methods
  //

}

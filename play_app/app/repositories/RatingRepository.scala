package repositories

import javax.inject.{Inject, Singleton}
import models.{Rating, RatingTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RatingRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, movieRepository: MovieRepository, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _rating = TableQuery[RatingTable]

  def getAll(): Future[Seq[Rating]] = db.run {
    _rating.result
  }

  def getById(ratingId: String): Future[Option[Rating]] = db.run {
    _rating.filter(_.id === ratingId).result.headOption
  }

}

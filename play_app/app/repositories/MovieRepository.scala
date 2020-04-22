package repositories

import javax.inject.{Inject, Singleton}
import models.{Movie, MovieTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class MovieRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  val _movie = TableQuery[MovieTable]

  //
  // methods
  //

}

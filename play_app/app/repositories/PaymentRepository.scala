package repositories

import javax.inject.{Inject, Singleton}
import models.{Payment, PaymentTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class PaymentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  val _payment = TableQuery[PaymentTable]

  //
  // methods
  //

}

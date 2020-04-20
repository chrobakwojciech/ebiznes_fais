package repositories

import javax.inject.{Inject, Singleton}
import models.Payment
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class PaymentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class PaymentTable(tag: Tag) extends Table[Payment](tag, "payment") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def price = column[Double]("price")

    def * = (id, name, price) <> ((Payment.apply _).tupled, Payment.unapply)
  }

  private val _payment = TableQuery[PaymentTable]

  //
  // methods
  //

}

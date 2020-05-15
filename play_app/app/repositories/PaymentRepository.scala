package repositories

import java.util.UUID

import javax.inject.{Inject, Singleton}
import models.{Payment, PaymentTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _payment = TableQuery[PaymentTable]

  def getAll(): Future[Seq[Payment]] = db.run {
    _payment.result
  }

  def getById(paymentId: String): Future[Option[Payment]] = db.run {
    _payment.filter(_.id === paymentId).result.headOption
  }

  def create(name: String): Future[Int] = db.run {
    val id: String = UUID.randomUUID().toString()
    _payment.insertOrUpdate(Payment(id, name))
  }

  def delete(paymentId: String): Future[Int] = db.run {
    _payment.filter(_.id === paymentId).delete
  }

}

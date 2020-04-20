package repositories

import javax.inject.{Inject, Singleton}
import models.Order
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository, paymentRepository: PaymentRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class OrderTable(tag: Tag) extends Table[Order](tag, "order") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def user = column[Long]("user")

    def user_fk = foreignKey("user_fk", user, _user)(_.id)

    def payment = column[Long]("payment")

    def payment_fk = foreignKey("payment_fk", payment, _payment)(_.id)

    def * = (id, user, payment) <> ((Order.apply _).tupled, Order.unapply)
  }

  import paymentRepository.PaymentTable
  import userRepository.UserTable

  private val _order = TableQuery[OrderTable]
  private val _user = TableQuery[UserTable]
  private val _payment = TableQuery[PaymentTable]

  //
  // methods
  //

}

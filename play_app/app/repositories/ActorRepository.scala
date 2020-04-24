package repositories


import javax.inject.{Inject, Singleton}
import models.{Actor, ActorTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ActorRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _actor = TableQuery[ActorTable]

  def getAll(): Future[Seq[Actor]] = db.run {
    _actor.result
  }

  def getById(actorId: String): Future[Option[Actor]] = db.run {
    _actor.filter(_.id === actorId).result.headOption
  }

}

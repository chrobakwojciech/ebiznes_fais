package repositories


import java.util.UUID

import javax.inject.{Inject, Singleton}
import models.{Actor, ActorTable, MovieActorTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ActorRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _actor = TableQuery[ActorTable]
  val _movieActors = TableQuery[MovieActorTable]

  def getAll(): Future[Seq[Actor]] = db.run {
    _actor.result
  }

  def getById(actorId: String): Future[Option[Actor]] = db.run {
    _actor.filter(_.id === actorId).result.headOption
  }

  def getForMovie(movieId: String): Future[Seq[Actor]] = db.run {
    _movieActors.filter(_.movie === movieId).join(_actor).on(_.actor === _.id).map {
      case (ma, a) => a
    }.result
  }

  def create(firstName: String, lastName: String, img: String): Future[Int] = db.run {
    val id: String = UUID.randomUUID().toString()
    _actor.insertOrUpdate(Actor(id, firstName, lastName, img))
  }

  def update(id: String, firstName: String, lastName: String, img: String): Future[Int] = db.run {
    _actor.filter(_.id === id).update(Actor(id, firstName, lastName, img))
  }

  def delete(actorId: String): Future[Int] = db.run {
    _actor.filter(_.id === actorId).delete
  }

}

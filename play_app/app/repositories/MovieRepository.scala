package repositories

import java.util.UUID

import javax.inject.{Inject, Singleton}
import models._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MovieRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val _movie = TableQuery[MovieTable]
  val _movieGenres = TableQuery[MovieGenreTable]
  val _movieActors = TableQuery[MovieActorTable]
  val _movieDirectors = TableQuery[MovieDirectorTable]
  val _order = TableQuery[OrderTable]
  val _orderItem = TableQuery[OrderItemTable]

  def getAll(): Future[Seq[Movie]] = db.run {
    _movie.result
  }

  def getById(movieId: String): Future[Option[Movie]] = db.run {
    _movie.filter(_.id === movieId).result.headOption
  }

  def isExist(movieId: String): Future[Boolean] = db.run {
    _movie.filter(_.id === movieId).exists.result
  }

  def getForGenre(genreId: String): Future[Seq[Movie]] = db.run {
    _movieGenres.filter(_.genre === genreId).join(_movie).on(_.movie === _.id).map {
      case (mg, m) => m
    }.result
  }

  def getForActor(actorId: String): Future[Seq[Movie]] = db.run {
    _movieActors.filter(_.actor === actorId).join(_movie).on(_.movie === _.id).map {
      case (ma, b) => b
    }.result
  }

  def getForDirector(directorId: String): Future[Seq[Movie]] = db.run {
    _movieDirectors.filter(_.director === directorId).join(_movie).on(_.movie === _.id).map {
      case (md, m) => m
    }.result
  }

  def getForUser(userId: String): Future[Seq[Movie]] = db.run {
    _order
      .filter(_.user === userId)
        .join(_orderItem).on(_.id === _.order)
        .join(_movie).on(_._2.movie === _.id)
        .map {
          case ((o, oi), m) => m
        }.distinct.result
  }

  def create(title: String, description: String, productionYear: String, price: Double, img: String, actors: Seq[String], directors: Seq[String], genres: Seq[String]) = {
    val id: String = UUID.randomUUID().toString()
    val createMovie = _movie.insertOrUpdate(Movie(id, title, description, productionYear, price, img))


    var actorSeq: Seq[MovieActor] = Seq()
    for (a <- actors) {
      actorSeq = actorSeq :+ MovieActor(id, a)
    }
    val bindActors = _movieActors ++= actorSeq


    var directorSeq: Seq[MovieDirector] = Seq()
    for (d <- directors) {
      directorSeq = directorSeq :+ MovieDirector(id, d)
    }
    val bindDirectors = _movieDirectors ++= directorSeq


    var genreSeq: Seq[MovieGenre] = Seq()
    for (g <- genres) {
      genreSeq = genreSeq :+ MovieGenre(id, g)
    }
    val bindGenres = _movieGenres ++= genreSeq

    db.run(DBIO.seq(createMovie, bindActors, bindDirectors, bindGenres).transactionally)
  }

  def delete(movieId: String) = {
    val deleteMovie = _movie.filter(_.id === movieId).delete
//    val deleteMovieActors = _movieActors.filter(_.movie === movieId).delete
//    val deleteMovieDirectors = _movieDirectors.filter(_.movie === movieId).delete
//    val deleteMovieGenres = _movieGenres.filter(_.movie === movieId).delete
    db.run(DBIO.seq(deleteMovie).transactionally)
  }

  def update(id: String, title: String, description: String, productionYear: String, price: Double, img: String, actors: Seq[String], directors: Seq[String], genres: Seq[String]) = {

    val updateMovie = _movie.filter(_.id === id).update(Movie(id, title, description, productionYear, price, img))


    var actorSeq: Seq[MovieActor] = Seq()
    for (a <- actors) {
      actorSeq = actorSeq :+ MovieActor(id, a)
    }
    val cleanActors = _movieActors.filter(_.movie === id).delete
    val bindActors = _movieActors ++= actorSeq

    var directorSeq: Seq[MovieDirector] = Seq()
    for (d <- directors) {
      directorSeq = directorSeq :+ MovieDirector(id, d)
    }
    val cleanDirectors = _movieDirectors.filter(_.movie === id).delete
    val bindDirectors = _movieDirectors ++= directorSeq


    var genreSeq: Seq[MovieGenre] = Seq()
    for (g <- genres) {
      genreSeq = genreSeq :+ MovieGenre(id, g)
    }
    val cleanGenres = _movieGenres.filter(_.movie === id).delete
    val bindGenres = _movieGenres ++= genreSeq


    db.run(DBIO.seq(
      updateMovie,
      cleanActors, bindActors,
      cleanDirectors, bindDirectors,
      cleanGenres, bindGenres
    ).transactionally)
  }

}

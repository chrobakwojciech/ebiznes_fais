// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/371847/Documents/ebiznes/ebiznes_fais/crud/conf/routes
// @DATE:Sat Apr 11 19:47:34 CEST 2020

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  HomeController_13: controllers.HomeController,
  // @LINE:9
  CountController_7: controllers.CountController,
  // @LINE:11
  AsyncController_12: controllers.AsyncController,
  // @LINE:14
  Assets_11: controllers.Assets,
  // @LINE:18
  UserController_9: controllers.UserController,
  // @LINE:25
  MovieController_10: controllers.MovieController,
  // @LINE:32
  CommentController_1: controllers.CommentController,
  // @LINE:37
  RatingController_2: controllers.RatingController,
  // @LINE:42
  ActorController_3: controllers.ActorController,
  // @LINE:49
  DirectorController_5: controllers.DirectorController,
  // @LINE:56
  GenreController_6: controllers.GenreController,
  // @LINE:63
  OrderController_0: controllers.OrderController,
  // @LINE:69
  OrderItemController_4: controllers.OrderItemController,
  // @LINE:76
  PaymentController_8: controllers.PaymentController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    HomeController_13: controllers.HomeController,
    // @LINE:9
    CountController_7: controllers.CountController,
    // @LINE:11
    AsyncController_12: controllers.AsyncController,
    // @LINE:14
    Assets_11: controllers.Assets,
    // @LINE:18
    UserController_9: controllers.UserController,
    // @LINE:25
    MovieController_10: controllers.MovieController,
    // @LINE:32
    CommentController_1: controllers.CommentController,
    // @LINE:37
    RatingController_2: controllers.RatingController,
    // @LINE:42
    ActorController_3: controllers.ActorController,
    // @LINE:49
    DirectorController_5: controllers.DirectorController,
    // @LINE:56
    GenreController_6: controllers.GenreController,
    // @LINE:63
    OrderController_0: controllers.OrderController,
    // @LINE:69
    OrderItemController_4: controllers.OrderItemController,
    // @LINE:76
    PaymentController_8: controllers.PaymentController
  ) = this(errorHandler, HomeController_13, CountController_7, AsyncController_12, Assets_11, UserController_9, MovieController_10, CommentController_1, RatingController_2, ActorController_3, DirectorController_5, GenreController_6, OrderController_0, OrderItemController_4, PaymentController_8, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_13, CountController_7, AsyncController_12, Assets_11, UserController_9, MovieController_10, CommentController_1, RatingController_2, ActorController_3, DirectorController_5, GenreController_6, OrderController_0, OrderItemController_4, PaymentController_8, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """count""", """controllers.CountController.count"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """message""", """controllers.AsyncController.message"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """users""", """controllers.UserController.getAll"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """users/""" + "$" + """userId<[^/]+>""", """controllers.UserController.get(userId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """users""", """controllers.UserController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """users/""" + "$" + """userId<[^/]+>""", """controllers.UserController.update(userId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """users/""" + "$" + """userId<[^/]+>""", """controllers.UserController.delete(userId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """movies""", """controllers.MovieController.getAll"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """movies/""" + "$" + """movieId<[^/]+>""", """controllers.MovieController.get(movieId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """movies""", """controllers.MovieController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """movies/""" + "$" + """movieId<[^/]+>""", """controllers.MovieController.update(movieId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """movies/""" + "$" + """movieId<[^/]+>""", """controllers.MovieController.delete(movieId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """comments""", """controllers.CommentController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """comments/""" + "$" + """commentId<[^/]+>""", """controllers.CommentController.update(commentId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """comments/""" + "$" + """commentId<[^/]+>""", """controllers.CommentController.delete(commentId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ratings""", """controllers.RatingController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ratings/""" + "$" + """ratingId<[^/]+>""", """controllers.CommentController.update(ratingId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ratings/""" + "$" + """ratingId<[^/]+>""", """controllers.CommentController.delete(ratingId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """actors""", """controllers.ActorController.getAll"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """actors/""" + "$" + """actorId<[^/]+>""", """controllers.ActorController.get(actorId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """actors""", """controllers.ActorController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """actors/""" + "$" + """actorId<[^/]+>""", """controllers.ActorController.update(actorId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """actors/""" + "$" + """actorId<[^/]+>""", """controllers.ActorController.delete(actorId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """directors""", """controllers.DirectorController.getAll"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """directors/""" + "$" + """directorId<[^/]+>""", """controllers.DirectorController.get(directorId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """directors""", """controllers.DirectorController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """directors/""" + "$" + """directorId<[^/]+>""", """controllers.DirectorController.update(directorId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """directors/""" + "$" + """directorId<[^/]+>""", """controllers.DirectorController.delete(directorId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """genres""", """controllers.GenreController.getAll"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """genres/""" + "$" + """genreId<[^/]+>""", """controllers.GenreController.get(genreId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """genres""", """controllers.GenreController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """genres/""" + "$" + """genreId<[^/]+>""", """controllers.GenreController.update(genreId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """genres/""" + "$" + """genreId<[^/]+>""", """controllers.GenreController.delete(genreId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """orders/""" + "$" + """userId<[^/]+>""", """controllers.OrderController.get(userId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """orders""", """controllers.OrderController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """orders/""" + "$" + """orderId<[^/]+>""", """controllers.OrderController.update(orderId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """orders/""" + "$" + """orderId<[^/]+>""", """controllers.OrderController.delete(orderId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """orders/""" + "$" + """orderId<[^/]+>/items""", """controllers.OrderItemController.get(orderId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """user/""" + "$" + """userId<[^/]+>/items""", """controllers.OrderItemController.getForUser(userId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """order-items""", """controllers.OrderItemController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """order-items/""" + "$" + """orderItemId<[^/]+>""", """controllers.OrderItemController.update(orderItemId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """order-items/""" + "$" + """orderItemId<[^/]+>""", """controllers.OrderItemController.delete(orderItemId:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """payments""", """controllers.PaymentController.getAll"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """payment""", """controllers.PaymentController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """payment/""" + "$" + """paymentId<[^/]+>""", """controllers.PaymentController.update(paymentId:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """payment/""" + "$" + """paymentId<[^/]+>""", """controllers.PaymentController.delete(paymentId:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:7
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_13.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_CountController_count1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("count")))
  )
  private[this] lazy val controllers_CountController_count1_invoker = createInvoker(
    CountController_7.count,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CountController",
      "count",
      Nil,
      "GET",
      this.prefix + """count""",
      """ An example controller showing how to use dependency injection""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_AsyncController_message2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("message")))
  )
  private[this] lazy val controllers_AsyncController_message2_invoker = createInvoker(
    AsyncController_12.message,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AsyncController",
      "message",
      Nil,
      "GET",
      this.prefix + """message""",
      """ An example controller showing how to write asynchronous code""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_Assets_versioned3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned3_invoker = createInvoker(
    Assets_11.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_UserController_getAll4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("users")))
  )
  private[this] lazy val controllers_UserController_getAll4_invoker = createInvoker(
    UserController_9.getAll,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "getAll",
      Nil,
      "GET",
      this.prefix + """users""",
      """ Users""",
      Seq()
    )
  )

  // @LINE:19
  private[this] lazy val controllers_UserController_get5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("users/"), DynamicPart("userId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_UserController_get5_invoker = createInvoker(
    UserController_9.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """users/""" + "$" + """userId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_UserController_create6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("users")))
  )
  private[this] lazy val controllers_UserController_create6_invoker = createInvoker(
    UserController_9.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "create",
      Nil,
      "POST",
      this.prefix + """users""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_UserController_update7_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("users/"), DynamicPart("userId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_UserController_update7_invoker = createInvoker(
    UserController_9.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """users/""" + "$" + """userId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val controllers_UserController_delete8_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("users/"), DynamicPart("userId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_UserController_delete8_invoker = createInvoker(
    UserController_9.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """users/""" + "$" + """userId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:25
  private[this] lazy val controllers_MovieController_getAll9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("movies")))
  )
  private[this] lazy val controllers_MovieController_getAll9_invoker = createInvoker(
    MovieController_10.getAll,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.MovieController",
      "getAll",
      Nil,
      "GET",
      this.prefix + """movies""",
      """ Movies""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_MovieController_get10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("movies/"), DynamicPart("movieId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_MovieController_get10_invoker = createInvoker(
    MovieController_10.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.MovieController",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """movies/""" + "$" + """movieId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:27
  private[this] lazy val controllers_MovieController_create11_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("movies")))
  )
  private[this] lazy val controllers_MovieController_create11_invoker = createInvoker(
    MovieController_10.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.MovieController",
      "create",
      Nil,
      "POST",
      this.prefix + """movies""",
      """""",
      Seq()
    )
  )

  // @LINE:28
  private[this] lazy val controllers_MovieController_update12_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("movies/"), DynamicPart("movieId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_MovieController_update12_invoker = createInvoker(
    MovieController_10.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.MovieController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """movies/""" + "$" + """movieId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:29
  private[this] lazy val controllers_MovieController_delete13_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("movies/"), DynamicPart("movieId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_MovieController_delete13_invoker = createInvoker(
    MovieController_10.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.MovieController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """movies/""" + "$" + """movieId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:32
  private[this] lazy val controllers_CommentController_create14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("comments")))
  )
  private[this] lazy val controllers_CommentController_create14_invoker = createInvoker(
    CommentController_1.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CommentController",
      "create",
      Nil,
      "POST",
      this.prefix + """comments""",
      """ Comments""",
      Seq()
    )
  )

  // @LINE:33
  private[this] lazy val controllers_CommentController_update15_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("comments/"), DynamicPart("commentId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CommentController_update15_invoker = createInvoker(
    CommentController_1.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CommentController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """comments/""" + "$" + """commentId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:34
  private[this] lazy val controllers_CommentController_delete16_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("comments/"), DynamicPart("commentId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CommentController_delete16_invoker = createInvoker(
    CommentController_1.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CommentController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """comments/""" + "$" + """commentId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:37
  private[this] lazy val controllers_RatingController_create17_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ratings")))
  )
  private[this] lazy val controllers_RatingController_create17_invoker = createInvoker(
    RatingController_2.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.RatingController",
      "create",
      Nil,
      "POST",
      this.prefix + """ratings""",
      """ Ratings""",
      Seq()
    )
  )

  // @LINE:38
  private[this] lazy val controllers_CommentController_update18_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ratings/"), DynamicPart("ratingId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CommentController_update18_invoker = createInvoker(
    CommentController_1.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CommentController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """ratings/""" + "$" + """ratingId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:39
  private[this] lazy val controllers_CommentController_delete19_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ratings/"), DynamicPart("ratingId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CommentController_delete19_invoker = createInvoker(
    CommentController_1.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CommentController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """ratings/""" + "$" + """ratingId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:42
  private[this] lazy val controllers_ActorController_getAll20_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("actors")))
  )
  private[this] lazy val controllers_ActorController_getAll20_invoker = createInvoker(
    ActorController_3.getAll,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ActorController",
      "getAll",
      Nil,
      "GET",
      this.prefix + """actors""",
      """ Actors""",
      Seq()
    )
  )

  // @LINE:43
  private[this] lazy val controllers_ActorController_get21_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("actors/"), DynamicPart("actorId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ActorController_get21_invoker = createInvoker(
    ActorController_3.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ActorController",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """actors/""" + "$" + """actorId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:44
  private[this] lazy val controllers_ActorController_create22_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("actors")))
  )
  private[this] lazy val controllers_ActorController_create22_invoker = createInvoker(
    ActorController_3.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ActorController",
      "create",
      Nil,
      "POST",
      this.prefix + """actors""",
      """""",
      Seq()
    )
  )

  // @LINE:45
  private[this] lazy val controllers_ActorController_update23_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("actors/"), DynamicPart("actorId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ActorController_update23_invoker = createInvoker(
    ActorController_3.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ActorController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """actors/""" + "$" + """actorId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:46
  private[this] lazy val controllers_ActorController_delete24_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("actors/"), DynamicPart("actorId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ActorController_delete24_invoker = createInvoker(
    ActorController_3.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ActorController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """actors/""" + "$" + """actorId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:49
  private[this] lazy val controllers_DirectorController_getAll25_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("directors")))
  )
  private[this] lazy val controllers_DirectorController_getAll25_invoker = createInvoker(
    DirectorController_5.getAll,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DirectorController",
      "getAll",
      Nil,
      "GET",
      this.prefix + """directors""",
      """ Directors""",
      Seq()
    )
  )

  // @LINE:50
  private[this] lazy val controllers_DirectorController_get26_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("directors/"), DynamicPart("directorId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_DirectorController_get26_invoker = createInvoker(
    DirectorController_5.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DirectorController",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """directors/""" + "$" + """directorId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:51
  private[this] lazy val controllers_DirectorController_create27_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("directors")))
  )
  private[this] lazy val controllers_DirectorController_create27_invoker = createInvoker(
    DirectorController_5.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DirectorController",
      "create",
      Nil,
      "POST",
      this.prefix + """directors""",
      """""",
      Seq()
    )
  )

  // @LINE:52
  private[this] lazy val controllers_DirectorController_update28_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("directors/"), DynamicPart("directorId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_DirectorController_update28_invoker = createInvoker(
    DirectorController_5.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DirectorController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """directors/""" + "$" + """directorId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:53
  private[this] lazy val controllers_DirectorController_delete29_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("directors/"), DynamicPart("directorId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_DirectorController_delete29_invoker = createInvoker(
    DirectorController_5.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DirectorController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """directors/""" + "$" + """directorId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:56
  private[this] lazy val controllers_GenreController_getAll30_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("genres")))
  )
  private[this] lazy val controllers_GenreController_getAll30_invoker = createInvoker(
    GenreController_6.getAll,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GenreController",
      "getAll",
      Nil,
      "GET",
      this.prefix + """genres""",
      """ Genres""",
      Seq()
    )
  )

  // @LINE:57
  private[this] lazy val controllers_GenreController_get31_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("genres/"), DynamicPart("genreId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_GenreController_get31_invoker = createInvoker(
    GenreController_6.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GenreController",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """genres/""" + "$" + """genreId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:58
  private[this] lazy val controllers_GenreController_create32_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("genres")))
  )
  private[this] lazy val controllers_GenreController_create32_invoker = createInvoker(
    GenreController_6.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GenreController",
      "create",
      Nil,
      "POST",
      this.prefix + """genres""",
      """""",
      Seq()
    )
  )

  // @LINE:59
  private[this] lazy val controllers_GenreController_update33_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("genres/"), DynamicPart("genreId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_GenreController_update33_invoker = createInvoker(
    GenreController_6.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GenreController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """genres/""" + "$" + """genreId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:60
  private[this] lazy val controllers_GenreController_delete34_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("genres/"), DynamicPart("genreId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_GenreController_delete34_invoker = createInvoker(
    GenreController_6.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GenreController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """genres/""" + "$" + """genreId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:63
  private[this] lazy val controllers_OrderController_get35_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("orders/"), DynamicPart("userId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_OrderController_get35_invoker = createInvoker(
    OrderController_0.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderController",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """orders/""" + "$" + """userId<[^/]+>""",
      """ Orders""",
      Seq()
    )
  )

  // @LINE:64
  private[this] lazy val controllers_OrderController_create36_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("orders")))
  )
  private[this] lazy val controllers_OrderController_create36_invoker = createInvoker(
    OrderController_0.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderController",
      "create",
      Nil,
      "POST",
      this.prefix + """orders""",
      """""",
      Seq()
    )
  )

  // @LINE:65
  private[this] lazy val controllers_OrderController_update37_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("orders/"), DynamicPart("orderId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_OrderController_update37_invoker = createInvoker(
    OrderController_0.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """orders/""" + "$" + """orderId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:66
  private[this] lazy val controllers_OrderController_delete38_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("orders/"), DynamicPart("orderId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_OrderController_delete38_invoker = createInvoker(
    OrderController_0.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """orders/""" + "$" + """orderId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:69
  private[this] lazy val controllers_OrderItemController_get39_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("orders/"), DynamicPart("orderId", """[^/]+""",true), StaticPart("/items")))
  )
  private[this] lazy val controllers_OrderItemController_get39_invoker = createInvoker(
    OrderItemController_4.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderItemController",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """orders/""" + "$" + """orderId<[^/]+>/items""",
      """ Order item""",
      Seq()
    )
  )

  // @LINE:70
  private[this] lazy val controllers_OrderItemController_getForUser40_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("user/"), DynamicPart("userId", """[^/]+""",true), StaticPart("/items")))
  )
  private[this] lazy val controllers_OrderItemController_getForUser40_invoker = createInvoker(
    OrderItemController_4.getForUser(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderItemController",
      "getForUser",
      Seq(classOf[String]),
      "GET",
      this.prefix + """user/""" + "$" + """userId<[^/]+>/items""",
      """""",
      Seq()
    )
  )

  // @LINE:71
  private[this] lazy val controllers_OrderItemController_create41_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("order-items")))
  )
  private[this] lazy val controllers_OrderItemController_create41_invoker = createInvoker(
    OrderItemController_4.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderItemController",
      "create",
      Nil,
      "POST",
      this.prefix + """order-items""",
      """""",
      Seq()
    )
  )

  // @LINE:72
  private[this] lazy val controllers_OrderItemController_update42_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("order-items/"), DynamicPart("orderItemId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_OrderItemController_update42_invoker = createInvoker(
    OrderItemController_4.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderItemController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """order-items/""" + "$" + """orderItemId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:73
  private[this] lazy val controllers_OrderItemController_delete43_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("order-items/"), DynamicPart("orderItemId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_OrderItemController_delete43_invoker = createInvoker(
    OrderItemController_4.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.OrderItemController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """order-items/""" + "$" + """orderItemId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:76
  private[this] lazy val controllers_PaymentController_getAll44_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("payments")))
  )
  private[this] lazy val controllers_PaymentController_getAll44_invoker = createInvoker(
    PaymentController_8.getAll,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PaymentController",
      "getAll",
      Nil,
      "GET",
      this.prefix + """payments""",
      """ Payments""",
      Seq()
    )
  )

  // @LINE:77
  private[this] lazy val controllers_PaymentController_create45_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("payment")))
  )
  private[this] lazy val controllers_PaymentController_create45_invoker = createInvoker(
    PaymentController_8.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PaymentController",
      "create",
      Nil,
      "POST",
      this.prefix + """payment""",
      """""",
      Seq()
    )
  )

  // @LINE:78
  private[this] lazy val controllers_PaymentController_update46_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("payment/"), DynamicPart("paymentId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_PaymentController_update46_invoker = createInvoker(
    PaymentController_8.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PaymentController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """payment/""" + "$" + """paymentId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:79
  private[this] lazy val controllers_PaymentController_delete47_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("payment/"), DynamicPart("paymentId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_PaymentController_delete47_invoker = createInvoker(
    PaymentController_8.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PaymentController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """payment/""" + "$" + """paymentId<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_13.index)
      }
  
    // @LINE:9
    case controllers_CountController_count1_route(params@_) =>
      call { 
        controllers_CountController_count1_invoker.call(CountController_7.count)
      }
  
    // @LINE:11
    case controllers_AsyncController_message2_route(params@_) =>
      call { 
        controllers_AsyncController_message2_invoker.call(AsyncController_12.message)
      }
  
    // @LINE:14
    case controllers_Assets_versioned3_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned3_invoker.call(Assets_11.versioned(path, file))
      }
  
    // @LINE:18
    case controllers_UserController_getAll4_route(params@_) =>
      call { 
        controllers_UserController_getAll4_invoker.call(UserController_9.getAll)
      }
  
    // @LINE:19
    case controllers_UserController_get5_route(params@_) =>
      call(params.fromPath[String]("userId", None)) { (userId) =>
        controllers_UserController_get5_invoker.call(UserController_9.get(userId))
      }
  
    // @LINE:20
    case controllers_UserController_create6_route(params@_) =>
      call { 
        controllers_UserController_create6_invoker.call(UserController_9.create)
      }
  
    // @LINE:21
    case controllers_UserController_update7_route(params@_) =>
      call(params.fromPath[String]("userId", None)) { (userId) =>
        controllers_UserController_update7_invoker.call(UserController_9.update(userId))
      }
  
    // @LINE:22
    case controllers_UserController_delete8_route(params@_) =>
      call(params.fromPath[String]("userId", None)) { (userId) =>
        controllers_UserController_delete8_invoker.call(UserController_9.delete(userId))
      }
  
    // @LINE:25
    case controllers_MovieController_getAll9_route(params@_) =>
      call { 
        controllers_MovieController_getAll9_invoker.call(MovieController_10.getAll)
      }
  
    // @LINE:26
    case controllers_MovieController_get10_route(params@_) =>
      call(params.fromPath[String]("movieId", None)) { (movieId) =>
        controllers_MovieController_get10_invoker.call(MovieController_10.get(movieId))
      }
  
    // @LINE:27
    case controllers_MovieController_create11_route(params@_) =>
      call { 
        controllers_MovieController_create11_invoker.call(MovieController_10.create)
      }
  
    // @LINE:28
    case controllers_MovieController_update12_route(params@_) =>
      call(params.fromPath[String]("movieId", None)) { (movieId) =>
        controllers_MovieController_update12_invoker.call(MovieController_10.update(movieId))
      }
  
    // @LINE:29
    case controllers_MovieController_delete13_route(params@_) =>
      call(params.fromPath[String]("movieId", None)) { (movieId) =>
        controllers_MovieController_delete13_invoker.call(MovieController_10.delete(movieId))
      }
  
    // @LINE:32
    case controllers_CommentController_create14_route(params@_) =>
      call { 
        controllers_CommentController_create14_invoker.call(CommentController_1.create)
      }
  
    // @LINE:33
    case controllers_CommentController_update15_route(params@_) =>
      call(params.fromPath[String]("commentId", None)) { (commentId) =>
        controllers_CommentController_update15_invoker.call(CommentController_1.update(commentId))
      }
  
    // @LINE:34
    case controllers_CommentController_delete16_route(params@_) =>
      call(params.fromPath[String]("commentId", None)) { (commentId) =>
        controllers_CommentController_delete16_invoker.call(CommentController_1.delete(commentId))
      }
  
    // @LINE:37
    case controllers_RatingController_create17_route(params@_) =>
      call { 
        controllers_RatingController_create17_invoker.call(RatingController_2.create)
      }
  
    // @LINE:38
    case controllers_CommentController_update18_route(params@_) =>
      call(params.fromPath[String]("ratingId", None)) { (ratingId) =>
        controllers_CommentController_update18_invoker.call(CommentController_1.update(ratingId))
      }
  
    // @LINE:39
    case controllers_CommentController_delete19_route(params@_) =>
      call(params.fromPath[String]("ratingId", None)) { (ratingId) =>
        controllers_CommentController_delete19_invoker.call(CommentController_1.delete(ratingId))
      }
  
    // @LINE:42
    case controllers_ActorController_getAll20_route(params@_) =>
      call { 
        controllers_ActorController_getAll20_invoker.call(ActorController_3.getAll)
      }
  
    // @LINE:43
    case controllers_ActorController_get21_route(params@_) =>
      call(params.fromPath[String]("actorId", None)) { (actorId) =>
        controllers_ActorController_get21_invoker.call(ActorController_3.get(actorId))
      }
  
    // @LINE:44
    case controllers_ActorController_create22_route(params@_) =>
      call { 
        controllers_ActorController_create22_invoker.call(ActorController_3.create)
      }
  
    // @LINE:45
    case controllers_ActorController_update23_route(params@_) =>
      call(params.fromPath[String]("actorId", None)) { (actorId) =>
        controllers_ActorController_update23_invoker.call(ActorController_3.update(actorId))
      }
  
    // @LINE:46
    case controllers_ActorController_delete24_route(params@_) =>
      call(params.fromPath[String]("actorId", None)) { (actorId) =>
        controllers_ActorController_delete24_invoker.call(ActorController_3.delete(actorId))
      }
  
    // @LINE:49
    case controllers_DirectorController_getAll25_route(params@_) =>
      call { 
        controllers_DirectorController_getAll25_invoker.call(DirectorController_5.getAll)
      }
  
    // @LINE:50
    case controllers_DirectorController_get26_route(params@_) =>
      call(params.fromPath[String]("directorId", None)) { (directorId) =>
        controllers_DirectorController_get26_invoker.call(DirectorController_5.get(directorId))
      }
  
    // @LINE:51
    case controllers_DirectorController_create27_route(params@_) =>
      call { 
        controllers_DirectorController_create27_invoker.call(DirectorController_5.create)
      }
  
    // @LINE:52
    case controllers_DirectorController_update28_route(params@_) =>
      call(params.fromPath[String]("directorId", None)) { (directorId) =>
        controllers_DirectorController_update28_invoker.call(DirectorController_5.update(directorId))
      }
  
    // @LINE:53
    case controllers_DirectorController_delete29_route(params@_) =>
      call(params.fromPath[String]("directorId", None)) { (directorId) =>
        controllers_DirectorController_delete29_invoker.call(DirectorController_5.delete(directorId))
      }
  
    // @LINE:56
    case controllers_GenreController_getAll30_route(params@_) =>
      call { 
        controllers_GenreController_getAll30_invoker.call(GenreController_6.getAll)
      }
  
    // @LINE:57
    case controllers_GenreController_get31_route(params@_) =>
      call(params.fromPath[String]("genreId", None)) { (genreId) =>
        controllers_GenreController_get31_invoker.call(GenreController_6.get(genreId))
      }
  
    // @LINE:58
    case controllers_GenreController_create32_route(params@_) =>
      call { 
        controllers_GenreController_create32_invoker.call(GenreController_6.create)
      }
  
    // @LINE:59
    case controllers_GenreController_update33_route(params@_) =>
      call(params.fromPath[String]("genreId", None)) { (genreId) =>
        controllers_GenreController_update33_invoker.call(GenreController_6.update(genreId))
      }
  
    // @LINE:60
    case controllers_GenreController_delete34_route(params@_) =>
      call(params.fromPath[String]("genreId", None)) { (genreId) =>
        controllers_GenreController_delete34_invoker.call(GenreController_6.delete(genreId))
      }
  
    // @LINE:63
    case controllers_OrderController_get35_route(params@_) =>
      call(params.fromPath[String]("userId", None)) { (userId) =>
        controllers_OrderController_get35_invoker.call(OrderController_0.get(userId))
      }
  
    // @LINE:64
    case controllers_OrderController_create36_route(params@_) =>
      call { 
        controllers_OrderController_create36_invoker.call(OrderController_0.create)
      }
  
    // @LINE:65
    case controllers_OrderController_update37_route(params@_) =>
      call(params.fromPath[String]("orderId", None)) { (orderId) =>
        controllers_OrderController_update37_invoker.call(OrderController_0.update(orderId))
      }
  
    // @LINE:66
    case controllers_OrderController_delete38_route(params@_) =>
      call(params.fromPath[String]("orderId", None)) { (orderId) =>
        controllers_OrderController_delete38_invoker.call(OrderController_0.delete(orderId))
      }
  
    // @LINE:69
    case controllers_OrderItemController_get39_route(params@_) =>
      call(params.fromPath[String]("orderId", None)) { (orderId) =>
        controllers_OrderItemController_get39_invoker.call(OrderItemController_4.get(orderId))
      }
  
    // @LINE:70
    case controllers_OrderItemController_getForUser40_route(params@_) =>
      call(params.fromPath[String]("userId", None)) { (userId) =>
        controllers_OrderItemController_getForUser40_invoker.call(OrderItemController_4.getForUser(userId))
      }
  
    // @LINE:71
    case controllers_OrderItemController_create41_route(params@_) =>
      call { 
        controllers_OrderItemController_create41_invoker.call(OrderItemController_4.create)
      }
  
    // @LINE:72
    case controllers_OrderItemController_update42_route(params@_) =>
      call(params.fromPath[String]("orderItemId", None)) { (orderItemId) =>
        controllers_OrderItemController_update42_invoker.call(OrderItemController_4.update(orderItemId))
      }
  
    // @LINE:73
    case controllers_OrderItemController_delete43_route(params@_) =>
      call(params.fromPath[String]("orderItemId", None)) { (orderItemId) =>
        controllers_OrderItemController_delete43_invoker.call(OrderItemController_4.delete(orderItemId))
      }
  
    // @LINE:76
    case controllers_PaymentController_getAll44_route(params@_) =>
      call { 
        controllers_PaymentController_getAll44_invoker.call(PaymentController_8.getAll)
      }
  
    // @LINE:77
    case controllers_PaymentController_create45_route(params@_) =>
      call { 
        controllers_PaymentController_create45_invoker.call(PaymentController_8.create)
      }
  
    // @LINE:78
    case controllers_PaymentController_update46_route(params@_) =>
      call(params.fromPath[String]("paymentId", None)) { (paymentId) =>
        controllers_PaymentController_update46_invoker.call(PaymentController_8.update(paymentId))
      }
  
    // @LINE:79
    case controllers_PaymentController_delete47_route(params@_) =>
      call(params.fromPath[String]("paymentId", None)) { (paymentId) =>
        controllers_PaymentController_delete47_invoker.call(PaymentController_8.delete(paymentId))
      }
  }
}

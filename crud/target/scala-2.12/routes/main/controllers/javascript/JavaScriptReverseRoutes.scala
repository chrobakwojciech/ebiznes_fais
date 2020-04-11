// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/371847/Documents/ebiznes/ebiznes_fais/crud/conf/routes
// @DATE:Sat Apr 11 19:47:34 CEST 2020

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:7
package controllers.javascript {

  // @LINE:14
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:69
  class ReverseOrderItemController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:70
    def getForUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderItemController.getForUser",
      """
        function(userId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "user/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("userId", userId0)) + "/items"})
        }
      """
    )
  
    // @LINE:71
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderItemController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "order-items"})
        }
      """
    )
  
    // @LINE:73
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderItemController.delete",
      """
        function(orderItemId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "order-items/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("orderItemId", orderItemId0))})
        }
      """
    )
  
    // @LINE:72
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderItemController.update",
      """
        function(orderItemId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "order-items/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("orderItemId", orderItemId0))})
        }
      """
    )
  
    // @LINE:69
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderItemController.get",
      """
        function(orderId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "orders/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("orderId", orderId0)) + "/items"})
        }
      """
    )
  
  }

  // @LINE:9
  class ReverseCountController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def count: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CountController.count",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "count"})
        }
      """
    )
  
  }

  // @LINE:76
  class ReversePaymentController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:79
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PaymentController.delete",
      """
        function(paymentId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "payment/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("paymentId", paymentId0))})
        }
      """
    )
  
    // @LINE:76
    def getAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PaymentController.getAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "payments"})
        }
      """
    )
  
    // @LINE:77
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PaymentController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "payment"})
        }
      """
    )
  
    // @LINE:78
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PaymentController.update",
      """
        function(paymentId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "payment/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("paymentId", paymentId0))})
        }
      """
    )
  
  }

  // @LINE:42
  class ReverseActorController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:44
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ActorController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "actors"})
        }
      """
    )
  
    // @LINE:42
    def getAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ActorController.getAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "actors"})
        }
      """
    )
  
    // @LINE:46
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ActorController.delete",
      """
        function(actorId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "actors/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("actorId", actorId0))})
        }
      """
    )
  
    // @LINE:45
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ActorController.update",
      """
        function(actorId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "actors/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("actorId", actorId0))})
        }
      """
    )
  
    // @LINE:43
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ActorController.get",
      """
        function(actorId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "actors/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("actorId", actorId0))})
        }
      """
    )
  
  }

  // @LINE:25
  class ReverseMovieController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:27
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.MovieController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "movies"})
        }
      """
    )
  
    // @LINE:25
    def getAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.MovieController.getAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "movies"})
        }
      """
    )
  
    // @LINE:29
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.MovieController.delete",
      """
        function(movieId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "movies/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("movieId", movieId0))})
        }
      """
    )
  
    // @LINE:28
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.MovieController.update",
      """
        function(movieId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "movies/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("movieId", movieId0))})
        }
      """
    )
  
    // @LINE:26
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.MovieController.get",
      """
        function(movieId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "movies/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("movieId", movieId0))})
        }
      """
    )
  
  }

  // @LINE:18
  class ReverseUserController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:20
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "users"})
        }
      """
    )
  
    // @LINE:18
    def getAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.getAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "users"})
        }
      """
    )
  
    // @LINE:22
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.delete",
      """
        function(userId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("userId", userId0))})
        }
      """
    )
  
    // @LINE:21
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.update",
      """
        function(userId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("userId", userId0))})
        }
      """
    )
  
    // @LINE:19
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.get",
      """
        function(userId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "users/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("userId", userId0))})
        }
      """
    )
  
  }

  // @LINE:7
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:49
  class ReverseDirectorController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:51
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DirectorController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "directors"})
        }
      """
    )
  
    // @LINE:49
    def getAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DirectorController.getAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "directors"})
        }
      """
    )
  
    // @LINE:53
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DirectorController.delete",
      """
        function(directorId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "directors/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("directorId", directorId0))})
        }
      """
    )
  
    // @LINE:52
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DirectorController.update",
      """
        function(directorId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "directors/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("directorId", directorId0))})
        }
      """
    )
  
    // @LINE:50
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DirectorController.get",
      """
        function(directorId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "directors/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("directorId", directorId0))})
        }
      """
    )
  
  }

  // @LINE:56
  class ReverseGenreController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:58
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GenreController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "genres"})
        }
      """
    )
  
    // @LINE:56
    def getAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GenreController.getAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "genres"})
        }
      """
    )
  
    // @LINE:60
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GenreController.delete",
      """
        function(genreId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "genres/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("genreId", genreId0))})
        }
      """
    )
  
    // @LINE:59
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GenreController.update",
      """
        function(genreId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "genres/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("genreId", genreId0))})
        }
      """
    )
  
    // @LINE:57
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GenreController.get",
      """
        function(genreId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "genres/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("genreId", genreId0))})
        }
      """
    )
  
  }

  // @LINE:11
  class ReverseAsyncController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:11
    def message: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.AsyncController.message",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "message"})
        }
      """
    )
  
  }

  // @LINE:37
  class ReverseRatingController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:37
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.RatingController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "ratings"})
        }
      """
    )
  
  }

  // @LINE:63
  class ReverseOrderController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:66
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderController.delete",
      """
        function(orderId0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "orders/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("orderId", orderId0))})
        }
      """
    )
  
    // @LINE:64
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "orders"})
        }
      """
    )
  
    // @LINE:65
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderController.update",
      """
        function(orderId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "orders/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("orderId", orderId0))})
        }
      """
    )
  
    // @LINE:63
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.OrderController.get",
      """
        function(userId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "orders/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("userId", userId0))})
        }
      """
    )
  
  }

  // @LINE:32
  class ReverseCommentController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:34
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CommentController.delete",
      """
        function(commentId0) {
        
          if (true) {
            return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "comments/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("commentId", commentId0))})
          }
        
        }
      """
    )
  
    // @LINE:32
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CommentController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "comments"})
        }
      """
    )
  
    // @LINE:33
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CommentController.update",
      """
        function(commentId0) {
        
          if (true) {
            return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "comments/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("commentId", commentId0))})
          }
        
        }
      """
    )
  
  }


}

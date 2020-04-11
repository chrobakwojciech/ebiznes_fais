// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/371847/Documents/ebiznes/ebiznes_fais/crud/conf/routes
// @DATE:Sat Apr 11 19:47:34 CEST 2020

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:7
package controllers {

  // @LINE:14
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:69
  class ReverseOrderItemController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:70
    def getForUser(userId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "user/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("userId", userId)) + "/items")
    }
  
    // @LINE:71
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "order-items")
    }
  
    // @LINE:73
    def delete(orderItemId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "order-items/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("orderItemId", orderItemId)))
    }
  
    // @LINE:72
    def update(orderItemId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "order-items/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("orderItemId", orderItemId)))
    }
  
    // @LINE:69
    def get(orderId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "orders/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("orderId", orderId)) + "/items")
    }
  
  }

  // @LINE:9
  class ReverseCountController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def count(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "count")
    }
  
  }

  // @LINE:76
  class ReversePaymentController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:79
    def delete(paymentId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "payment/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("paymentId", paymentId)))
    }
  
    // @LINE:76
    def getAll(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "payments")
    }
  
    // @LINE:77
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "payment")
    }
  
    // @LINE:78
    def update(paymentId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "payment/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("paymentId", paymentId)))
    }
  
  }

  // @LINE:42
  class ReverseActorController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:44
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "actors")
    }
  
    // @LINE:42
    def getAll(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "actors")
    }
  
    // @LINE:46
    def delete(actorId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "actors/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("actorId", actorId)))
    }
  
    // @LINE:45
    def update(actorId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "actors/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("actorId", actorId)))
    }
  
    // @LINE:43
    def get(actorId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "actors/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("actorId", actorId)))
    }
  
  }

  // @LINE:25
  class ReverseMovieController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:27
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "movies")
    }
  
    // @LINE:25
    def getAll(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "movies")
    }
  
    // @LINE:29
    def delete(movieId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "movies/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("movieId", movieId)))
    }
  
    // @LINE:28
    def update(movieId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "movies/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("movieId", movieId)))
    }
  
    // @LINE:26
    def get(movieId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "movies/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("movieId", movieId)))
    }
  
  }

  // @LINE:18
  class ReverseUserController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:20
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "users")
    }
  
    // @LINE:18
    def getAll(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "users")
    }
  
    // @LINE:22
    def delete(userId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("userId", userId)))
    }
  
    // @LINE:21
    def update(userId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("userId", userId)))
    }
  
    // @LINE:19
    def get(userId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "users/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("userId", userId)))
    }
  
  }

  // @LINE:7
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:49
  class ReverseDirectorController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:51
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "directors")
    }
  
    // @LINE:49
    def getAll(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "directors")
    }
  
    // @LINE:53
    def delete(directorId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "directors/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("directorId", directorId)))
    }
  
    // @LINE:52
    def update(directorId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "directors/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("directorId", directorId)))
    }
  
    // @LINE:50
    def get(directorId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "directors/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("directorId", directorId)))
    }
  
  }

  // @LINE:56
  class ReverseGenreController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:58
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "genres")
    }
  
    // @LINE:56
    def getAll(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "genres")
    }
  
    // @LINE:60
    def delete(genreId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "genres/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("genreId", genreId)))
    }
  
    // @LINE:59
    def update(genreId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "genres/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("genreId", genreId)))
    }
  
    // @LINE:57
    def get(genreId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "genres/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("genreId", genreId)))
    }
  
  }

  // @LINE:11
  class ReverseAsyncController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:11
    def message(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "message")
    }
  
  }

  // @LINE:37
  class ReverseRatingController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:37
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "ratings")
    }
  
  }

  // @LINE:63
  class ReverseOrderController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:66
    def delete(orderId:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "orders/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("orderId", orderId)))
    }
  
    // @LINE:64
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "orders")
    }
  
    // @LINE:65
    def update(orderId:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "orders/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("orderId", orderId)))
    }
  
    // @LINE:63
    def get(userId:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "orders/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("userId", userId)))
    }
  
  }

  // @LINE:32
  class ReverseCommentController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:34
    def delete(commentId:String): Call = {
    
      (commentId: @unchecked) match {
      
        // @LINE:34
        case (commentId)  =>
          
          Call("DELETE", _prefix + { _defaultPrefix } + "comments/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("commentId", commentId)))
      
      }
    
    }
  
    // @LINE:32
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "comments")
    }
  
    // @LINE:33
    def update(commentId:String): Call = {
    
      (commentId: @unchecked) match {
      
        // @LINE:33
        case (commentId)  =>
          
          Call("PUT", _prefix + { _defaultPrefix } + "comments/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("commentId", commentId)))
      
      }
    
    }
  
  }


}

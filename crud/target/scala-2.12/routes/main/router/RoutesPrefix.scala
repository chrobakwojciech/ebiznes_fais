// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/371847/Documents/ebiznes/ebiznes_fais/crud/conf/routes
// @DATE:Sat Apr 11 19:47:34 CEST 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}

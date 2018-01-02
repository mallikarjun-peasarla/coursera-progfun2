package eg.coursera.week1

object PartialMatches {

  def pingPong1(inMsg: String): String = {
    inMsg match {
      case "ping" => "pong"
    }
  }

  val f1: String => String = { case "ping" => "pong"}
  val f2: PartialFunction[String, String] = { case "ping" => "pong"}
  val f3: PartialFunction[String, String] = PartialFunction({ case "ping" => "pong"})

  val f4 = new PartialFunction[String, String] {
    def apply(x: String) = x match {
      case "ping" => "pong"
    }
    def isDefinedAt(x: String) = x match {
      case "ping" => true
      case _ => false
    }
  }

  def pingPong2(inMsg: String): String = f2(inMsg)


  def main(args: Array[String]): Unit = {
    println(pingPong1("ping"))
    println(pingPong2("ping"))
    println(f1("ping"))
    println(f2("ping"))

    println(s"f2.isDefinedAt('ping'): ${f2.isDefinedAt("ping")}")
    println(s"f2.isDefinedAt('pong'): ${f2.isDefinedAt("pong")}")
    Thread.sleep(100)
    println(f3("abc"))
  }
}

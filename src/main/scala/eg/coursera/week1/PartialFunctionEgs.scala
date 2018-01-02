package eg.coursera.week1

object PartialFunctionEgs {

  val f: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: y :: rest => "two"
  }

  val g: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest =>
      rest match {
        case Nil => "two"
      }
  }

  val hpart: PartialFunction[List[Int], String] = {
    case Nil => "two"
  }
  val h: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest => hpart(rest)               // isDefined will not evaluate isDefined on inner function
  }

  def main(args: Array[String]): Unit = {
    println(f.isDefinedAt(List(1, 2, 3)))
    println(g.isDefinedAt(List(1, 2, 3)))
    println(h.isDefinedAt(List(1, 2, 3)))
    println(hpart.isDefinedAt(List(2, 3)))

    println(f(List(1, 2, 3)))
    Thread.sleep(100)
    println(g(List(1, 2, 3)))

  }
}

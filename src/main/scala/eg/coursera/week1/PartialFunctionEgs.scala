package eg.coursera.week1

/* when to use partial functions:
  you want to define a series of functions that only work for a subset of input values,
  and combine those functions to completely solve a problem.
*/
object PartialFunctionEgs {

  val f: PartialFunction[List[Int], String] = {
    case Nil => "empty"
    case x :: y :: rest => "non-empty"
  }

  val g: PartialFunction[List[Int], String] = {
    case Nil => "empty"
    case x :: rest =>
      rest match {
        case Nil => "one element"
      }
  }

  val g2: PartialFunction[List[Int], String] = new PartialFunction[List[Int], String] {
    def apply(list: List[Int]) = {
      list match {
        case x :: y :: Nil => "two elements"
      }
    }
    def isDefinedAt(list: List[Int]): Boolean = if(list.size == 2) true else false
  }

  val g1: PartialFunction[List[Int], String] = new PartialFunction[List[Int], String] {
    def apply(list: List[Int]) = {
      list match {
        case Nil => "empty"
        case x :: Nil => "one element"
      }
    }
    def isDefinedAt(list: List[Int]): Boolean = if(list.size < 2) true else false
  }


  val h: Function1[List[Int], String] = {
    case Nil => "empty"
    case x :: rest =>
      rest match {
        case Nil => "one element"
      }
  }

  val jpart: PartialFunction[List[Int], Int] = {
    case Nil => 0
    case x :: rest => 1
  }
  val j: PartialFunction[List[Int], Int] = {
    case Nil => 0
    case x :: rest => 1 + jpart(rest)               // isDefined will not evaluate isDefined on inner function
  }

  val k: PartialFunction[List[Int], Int] = {
    case Nil => 0
    case x :: rest => 1 + k(rest)               // isDefined true as same function used recursively
  }

  def main(args: Array[String]): Unit = {
    val list1 = List(1)
    val list2 = List(1, 2)
    val list3 = List(1, 2, 3)

    println(f.isDefinedAt(list3))
    println(g.isDefinedAt(list3))
    println(g1.isDefinedAt(list3))
    println(j.isDefinedAt(list3))
    println(jpart.isDefinedAt(list3))

    Thread.sleep(1000)
    println()
    println(f(list3))
    if(g.isDefinedAt(list3)) handleException(g, list3)
    handleException(h(list3))

    if(g1.isDefinedAt(list3)) {
      println(g1(list3))
    } else {
      println("partial function not defined for input")
    }

    println("----demo orElse start---")
    println(Some(list1).map(g1 orElse g2))
    println(Some(list2).map(g1 orElse g2))
    val g2Def = g1 orElse g2
    println(g2Def(list1))
    println((g1 orElse g2)(list2))
    println("----demo orElse End---")

    println(j(List(1, 2, 3, 4)))
    println(k(List(1, 0, 7, 3)))

  }

  def handleException(exp: => String) {
    try {
      val result = exp
      println(result)
    } catch {
      case e: Exception => println(e)
    }
  }

  def handleException(fn: List[Int] => String, list: List[Int]): Unit = {
    try {
      println(fn(list))
    } catch {
      case e: Exception => println(e)
    }
  }
}

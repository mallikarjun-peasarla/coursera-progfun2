package eg.coursera.week1

object TranslationOfFor {

  val e1 = List("four", "three", "one", "two")  //return monad
  val e2: PartialFunction[String, Int] = {
    case "zero" => 0
    case "one" => 1
    case "two" => 2
    case "three" => 3
    case "four" => 4
    case "five" => 5
    case "six" => 6
    case "seven" => 7
    case "eight" => 8
  }
  val e3: PartialFunction[Int, String] = {
    case x: Int => x.toString
  }
  val e4: PartialFunction[String, List[Int]] = {
    case "zero" => List(0)
    case "one" => List(1)
    case "two" => List(2)
    case "three" => List(3)
    case "four" => List(4)
    case "five" => List(5)
    case "six" => List(6)
    case "seven" => List(7)
    case "eight" => List(8)
  }

  // filter less than four
  val f: PartialFunction[String, Boolean] = {
    case "four" => false
    case _ => true
  }
  // multiply by 2 and modulus 10
  val s: PartialFunction[String, String] = {
    case "one" => "two"
    case "two" => "four"
    case "three" => "six"
    case "four" => "eight"
    case "five" => "zero"
    case "six" => "two"
    case "seven" => "four"
    case "eight" => "six"
  }


  def main(args: Array[String]): Unit = {
    // Translation of for 1
    val for1 = for (x <- e1) yield e2(x)     // e1 is a monad or at-least a class which implement map or flatmap
    val map1 = e1.map(x => e2(x))
    println(for1)
    assert(for1 == map1)

    // Translation of for 2
    val for2 = for (x <- e1 if f(x)) yield e2(x)
    val fm2 = for (x <- e1.withFilter(x => f(x))) yield e2(x)
    val map2 = e1.filter(x => f(x)).map(x => e2(x))
    println(for2)
    assert(for2 == fm2)
    assert(for2 == map2)

//    val for2 = for (x <- e1 if f(x); y <- s(x)) yield e2(y)
//    val fm2 = for (x <- e1.withFilter(x => f(x)); y <- s(x)) yield e2(y)
//    val map2 = e1.filter(x => f(x)).map(x => s1(x))

    // Translation of for 2
    val for3 = for (x <- e1; y <- e4(x)) yield e3(y)
    val fm3 = e1.flatMap(x => for (y <- e4(x)) yield e3(y))
    val map3 = e1.flatMap(x => e4(x).map(y => e3(y)))
    println(for3)
    assert(for3 == fm3)
    assert(for3 == map3)

    //one more eg at ForComprehension.scala
  }
}

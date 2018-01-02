package eg.coursera.reactivecheatsheet

trait Coin {}
case class Gold() extends Coin {}
case class Silver() extends Coin {}

object PartialFunctionDemo {
  val pf: PartialFunction[Coin, String] = {
    case Gold() => "a golden coin"
    // no case for Silver(), because we're only interested in Gold()
  }

  def main(args: Array[String]): Unit = {
    println(pf.isDefinedAt(Gold()))   // true
    println(pf.isDefinedAt(Silver())) // false
    println(pf(Gold()))               // a golden coin
    println(pf(Silver()))             // throws a scala.MatchError
  }
}

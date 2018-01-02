package eg.coursera.cheatsheet

import math.Ordering

object OrderingDemo {
  def main(args: Array[String]): Unit = {
    val fruits = List("Mango", "Apple", "Oragne", "Banana")
    println(msort(fruits))
  }

  def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    xs.sorted   // check this code
  }

}

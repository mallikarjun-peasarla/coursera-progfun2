package eg.coursera.cheatsheet

object HigherOrderFunctions {
  // sum() returns a function that takes two integers and returns an integer
  def sumOfFunction(f: Int => Int): (Int, Int) => Int = {
    def sumf(a: Int, b: Int): Int = {f(a) + f(b)}
    sumf
  }

  def sumOfFunction2(f: Int => Int): Function2[Int, Int, Int] = {
    def sumf(a: Int, b: Int): Int = {f(a) + f(b)}
    sumf
  }

  // same as above. Its type is (Int => Int) => (Int, Int) => Int
  def sumOfFn(f: Int => Int)(a: Int, b: Int): Int = { f(a) + f(b) }

  def cube(x: Int) = x * x * x

  def main(args: Array[String]): Unit = {
    val resultWithCurr = sumOfFn(cube)(1, 4)
    println(resultWithCurr)

    val resultCallRetFn = sumOfFunction(cube)(1,4)
    println(resultCallRetFn)

    val resultCallRetFn2 = sumOfFunction2(cube)(1,4)
    println(resultCallRetFn2)
  }

}

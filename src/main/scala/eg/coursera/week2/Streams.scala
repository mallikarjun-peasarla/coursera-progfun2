package eg.coursera.week2

object Streams {

  def nthPrime(from: Int, to: Int, n: Int): Int =
    if (from >= to) throw new Error("no prime")
  else if (isPrime(from))
    if (n == 1) from else nthPrime(from + 1, to, n - 1)
  else nthPrime(from + 1, to, n)

  def isPrime(n: Int): Boolean = (2 to math.sqrt(n).toInt) forall (x => n % x != 0)

  def streamRange(lo: Int, hi: Int): Stream[Int] = {
    println(s"lo: $lo")
    if (lo >= hi) Stream.empty
    else Stream.cons(lo, streamRange(lo + 1, hi))
  }

  def listRange(lo: Int, hi: Int): List[Int] =
    if (lo >= hi) Nil
    else lo :: listRange(lo + 1, hi)

  def main(args: Array[String]): Unit = {
    val x = streamRange(1, 10).take(3)
    println(x)
    println(x.tail)

    val result = (streamRange(1000, 10000) filter isPrime) apply 1
    println(result)
  }

}

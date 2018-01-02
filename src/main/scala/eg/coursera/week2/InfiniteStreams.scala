package eg.coursera.week2

object InfiniteStreams {

  def from(n: Int): Stream[Int] = n #:: from(n+1)

  def sieve(s: Stream[Int]): Stream[Int] = s.head #:: sieve(s.tail filter (_ % s.head != 0))

  def sqrtStream(x: Double): Stream[Double] = {
    def improve(guess: Double) = (guess + x / guess) / 2
    lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
    guesses
  }
  def isGoodEnough(guess: Double, x: Double) =  math.abs((guess * guess - x) / x) < 0.0001

  def main(args: Array[String]): Unit = {
    // infinite natural numbers
    val allNaturalNumbers = from(0)
    val allMultiplesOfFour =  allNaturalNumbers map(_ * 4)
    println(allNaturalNumbers)
    println(allMultiplesOfFour)
    println(allMultiplesOfFour.take(100))

    // infinite primes
    val primes = sieve(from(2))      // primes from 2
    println((primes take 50).toList)

    val primesFrom100 = sieve(from(100))      // did not work ??
    println((primesFrom100 take 10).toList)

    // square root stream
    val squareRootStream = sqrtStream(16) filter (isGoodEnough(_, 16))
    println(squareRootStream)
    println(squareRootStream.take(10).toList)
  }

}

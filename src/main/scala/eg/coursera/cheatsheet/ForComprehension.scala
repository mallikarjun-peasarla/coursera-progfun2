package eg.coursera.cheatsheet

// for-comprehension is syntactic sugar for map, flatMap and filter operations on collections
object ForComprehension {
  def main(args: Array[String]): Unit = {
    val M = 5
    val N = 3

    // for (x <- e1) yield e2 is translated to e1.map(x => e2)
    // for (x <- e1; y <- e2) yield e3 is translated to e1.flatMap(x => for (y <- e2) yield e3)
    val f1 = for (x <- 1 to M; y <- 1 to N)
      yield (x,y)
    //equivalent to
    val m1 = (1 to M) flatMap (x => (1 to N) map (y => (x, y)))
    println(f1)
    assert(f1 == m1)

    // for (x <- e1 if f) yield e2 is translated to for (x <- e1.filter(x => f)) yield e2
    val n = 5
    val i = 200
    val j = 201
    val f2 = for {
      i <- 1 until n
      j <- 1 until i
      if isPrime4(i + j)
    } yield (i, j)
    //same as
    val fs2 = for (i <- 1 until n; j <- 1 until i if isPrime4(i + j))
      yield (i, j)
    //equivalent to
    val m2 = (1 until n).flatMap(i => (1 until i).filter(j => isPrime4(i + j)).map(j => (i, j)))
    println(m2)
    assert(f2 == fs2)
    assert(f2 == m2)


  }

  def isPrime1(i :Int) : Boolean = {
    if (i <= 1)
      false
    else if (i == 2)
      true
    else
      !(2 to (i-1)).exists(x => i % x == 0)
  }
  def isPrime2(n: Int): Boolean = ! ((2 until n-1) exists (n % _ == 0))
  //def isPrime3(n: Int): Boolean = ! ((2 until math.sqrt(n).toInt) exists (n % _ == 0))
  def isPrime4(n: Int): Boolean = (2 to math.sqrt(n).toInt) forall (x => n % x != 0)

}

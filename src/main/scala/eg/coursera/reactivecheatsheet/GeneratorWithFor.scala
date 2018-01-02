package eg.coursera.reactivecheatsheet

trait Generator[+T] { self =>
  def generate: T
  def map[S](f: T => S) : Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }
  def flatMap[S](f: T => Generator[S]) : Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}

object GeneratorWithFor {
  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }

  val booleans = for {x <- integers} yield x > 0
  val pairs = for {x <- integers; y<- integers} yield (x, y)

  def single[T](x: T): Generator[T] = new Generator[T] {
    def generate = x
  }
  def chooseFromRange(lo: Int, hi: Int) : Generator[Int] = for {x <- integers } yield lo + x % (hi - lo)
  def oneOf[T](xs: T*): Generator[T] = for (idx <- chooseFromRange(0, xs.length)) yield xs(idx)

  def lists: Generator[List[Int]] = for {
    isEmpty <- booleans
    list <- if (isEmpty) emptyLists else nonEmptyLists
  } yield list
  def emptyLists = single(Nil)
  def nonEmptyLists = for {
    head <- integers
    tail <- lists
  } yield head :: tail

  // trial
  object Integer2 extends Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }

  val bool2 = for {x <- Integer2 } yield x > 0
  val bool3 = Integer2.map{x => x > 0}

  def main(args: Array[String]): Unit = {
    println(integers.generate)
    println(booleans.generate)
    println(pairs.generate)
    println(chooseFromRange(integers.generate, integers.generate).generate)

    println(bool2.generate)
    println(bool3.generate)

    test(pairs) ({                // scalacheck is a better tool test generators
      case (xs, ys) => xs != ys
    })
  }

  def test[T](g: Generator[T], numTimes: Int = 100)
             (test: T => Boolean): Unit = {
    for (i <- 0 until numTimes) {
      val value = g.generate
      assert(test(value), "test failed for "+value)
    }
    println("passed "+numTimes+" tests")
  }
}

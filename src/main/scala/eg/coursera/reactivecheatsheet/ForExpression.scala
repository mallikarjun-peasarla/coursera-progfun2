package eg.coursera.reactivecheatsheet

object ForExpression {
  def main(args: Array[String]): Unit = {
    // Associativily helps us “inline” nested for-expressions and write something like
    val f1 = for (x <- 1 to 5; y <- 1 to 3)
      yield (x,y)

    //
    //for{x <- m} yield x == m
  }
}

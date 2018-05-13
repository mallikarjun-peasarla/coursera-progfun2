package eg.coursera.week3

object ForLoopFunction {

  def main(args: Array[String]): Unit = {
    // for (int i = 1; i < 3; i = i + 1) { System.out.print(i + " "); }
    for (i <- 1 until 3) { System.out.print(i + " ") }
    System.out.println();
    for (i <- 1 until 3; j <- "abc") println(i + " " + j)
    // (1 until 3) foreach (i => "abc" foreach (j => println(i + " " + j)))

  }
}

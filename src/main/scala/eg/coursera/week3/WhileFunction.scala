package eg.coursera.week3

object WhileFunction {
  def power (x: Double, exp: Int): Double = {
    var r = 1.0
    var i = exp
    while (i > 0) { r = r * x; i = i - 1 }
    r
  }

  def WHILE(condition: => Boolean)(command: => Unit): Unit =
    if (condition) {
      command
      WHILE(condition)(command)
    }
    else ()

  def power2 (x: Double, exp: Int): Double = {
    var r = 1.0
    var i = exp
    WHILE (i > 0) { r = r * x; i = i - 1 }
    r
  }

  def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
    command
    if (condition) ()
    else {
      REPEAT(command)(condition)
    }
  }

  def REPEATUNTIL(command: => Unit)(condition: => Boolean): Unit = {
    command
    if (condition) {
      REPEATUNTIL(command)(condition)
    }
    else ()
  }

  def main(args: Array[String]): Unit = {
    val res1 = power(3.0, 2)
    println(res1)

    val res2 = power(3.0, 2)
    println(res2)
  }

}

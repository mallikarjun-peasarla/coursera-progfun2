package eg.coursera.week4

import scala.collection.mutable.ListBuffer

object FunctionVsExpressionSynt {
  def main(args: Array[String]): Unit = {
    val list = ListBuffer("a", "b", "c")

    val filterFn1: Function0[ListBuffer[String]] = {() =>
      list.filter(_!="b")
    }

    val filterFn12: () => ListBuffer[String] = {() =>
      def fn(): ListBuffer[String] = {list.filter(_!="b")}
      fn
    }

    val filterFn2: () => ListBuffer[String] = {() =>
      list.filter(_!="b")
    }

    evalExpression(filterFn1)
    evalExpression({() => list.filter(_!="b")})
    evalExpression("any-string")
    println()

    evalExpression(list.filter(_!="b"))
    evalFunction1(filterFn1)
    evalFunction2(filterFn2)

    list += "d"
    evalExpression(list.filter(_!="b"))
    evalFunction1(filterFn1)
    evalFunction2(filterFn2)
  }

  def evaluate[T](list: List[T]): Unit = {
    evalExpression(list.filter(_!="b"))
  }

  def evalExpression[T](expr: => T) = log(expr)
  def evalFunction1[T](fn: Function0[T]) = log(fn())
  def evalFunction2[T](fn: () => T) = log(fn())

  def log[T](t: T) = println("log: "+t)

}


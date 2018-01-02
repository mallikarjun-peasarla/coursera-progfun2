package eg.coursera.reactivecheatsheet

import scala.concurrent.Promise
import scala.util.Try
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object Promises {

  def futureTest(): Future[Int] = Future {
    val n = 3
    1 to n foreach { n =>
      Thread.sleep(1000)
    }
    n
  }

  def main(args: Array[String]): Unit = {
    val p = Promise[Int]()
    val f = futureTest()
    p.completeWith(f)

    p.future onComplete {
      case x => println(x.get)
    }
    Thread.sleep(5*1000)
  }


//  def promiseDemo[T]() = {
//    val p = Promise[T]  // defines a promise
//    p.future            // returns a future that will complete when p.complete() is called
//    p.complete(Try[T])  // completes the future
//    p success T         // successfully completes the promise
//  }

}

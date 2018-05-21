package eg.coursera.reactivecheatsheet

import scala.async.Async._
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object AsyncAwait {

  def retry[T](noTimes: Int)(block: => Future[T]): Future[T] = async {
    var i = 0;
    var result: Try[T] = Failure(new Exception("Problem!"))
    while (i < noTimes && result.isFailure) {
      result = Success (await { block })
      i += 1
    }
    result.get
  }

  def main(args: Array[String]): Unit = {
    val async = asyncTest()
    val fut = futureTest()
    println("waiting")
    futResult(async)
    futResult(fut)
    Thread.sleep(10*1000)
  }

  def asyncTest(): Future[Int] = async[Int] {
    val n = 5
    1 to n foreach { n =>
      Thread.sleep(1000)
    }
    n
  }

  def futureTest(): Future[Int] = Future {
    val n = 7
    1 to n foreach { n =>
      Thread.sleep(1000)
    }
    n
  }

  def futResult[T](future: Future[T]) = future.onComplete {
    case n: Try[Int] =>
      println("res: "+n.get)
  }
}

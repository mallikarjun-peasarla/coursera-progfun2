package eg.coursera.reactivecheatsheet

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.async.Async._

class FutureUtils[T] {
  def retry(noTimes: Int)(block: => Future[T]): Future[T] = async {
    var i = 0;
    var result: Try[T] = Failure(new Exception("Problem!"))
    while (i < noTimes && result.isFailure) {
      result = Success (await { block })
      i += 1
    }
    result.get
  }
}
object MonadsAwait {

  def answerToLife : Future[Int] = Future {
    Thread.sleep(10000) // wait for 1000 millisecond
    42
  }

  def main(args: Array[String]): Unit = {
    val ans2 = for {
      r <- answerToLife
    } yield r
    println(ans2)

    val futUtils = new FutureUtils[Int]
    futUtils.retry(3)(answerToLife)
  }

}

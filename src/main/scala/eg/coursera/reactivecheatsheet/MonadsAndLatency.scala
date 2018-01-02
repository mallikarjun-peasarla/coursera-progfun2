package eg.coursera.reactivecheatsheet

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object MonadsAndLatency {

  def answerToLife : Future[Int] = Future { 42 }

  def main(args: Array[String]): Unit = {
    val ans1 = answerToLife onComplete {
      case Success(result)           => result        // returns 42
      case Failure(e) => println("An error has occured: " + e.getMessage)  // returns Failure(java.Lang.Exception: WRONG)
    }
    println(ans1)

    answerToLife onSuccess {
      case result => result
    }

    answerToLife onFailure {
      case t => println("An error has occured: " + t.getMessage)
    }

    //answerToLife.now    // only works if the future is completed

    val ans = for {
      r <- answerToLife
    } yield r
    println(ans)
  }

}

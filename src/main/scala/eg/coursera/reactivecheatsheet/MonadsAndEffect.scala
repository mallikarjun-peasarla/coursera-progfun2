package eg.coursera.reactivecheatsheet

import scala.util.{Failure, Success, Try}

object MonadsAndEffect {
  def main(args: Array[String]): Unit = {
    val ans1 = answerToLife(42) match {
      case Success(t)           => t        // returns 42
      case failure @ Failure(e) => failure  // returns Failure(java.Lang.Exception: WRONG)
    }
    println(ans1)

    // Monad with effect can handle exceptions elegantly, because the methods have Try[T] return type
    println("\n--- with monad ----")
    val ans2 = for {
      a1 <- answerToLife(42)
      a2 <- answerToLife(43)
    } yield a2
    println(ans2)

  }

  def answerToLife(nb: Int) : Try[Int] = {
    if (nb == 42) Success(nb)
    else Failure(new Exception("WRONG"))
  }

}

//Try satisfying two Monad conditions
/*
abstract class Try[T]
  def map[U](f: T => U): Try[U]
  def flatMap[U](f: T => Try[U]): Try[U]

case class Success[T](elem: T) extends Try[T]
  override def map[U](f: T => U): Try[U] = Try[U](f(value))
  override def flatMap[U](f: T => Try[U]): Try[U] =
    try f(value) catch { case NonFatal(e) => Failure(e) }

case class Failure(t: Throwable) extends Try[Nothing]
  override def map[U](f: T => U): Try[U] = this.asInstanceOf[Try[U]]
  override def flatMap[U](f: T => Try[U]): Try[U] = this.asInstanceOf[Try[U]]

*/
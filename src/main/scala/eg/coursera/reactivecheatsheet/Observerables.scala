package eg.coursera.reactivecheatsheet

import rx.lang.scala.JavaConversions.toJavaObservable
import rx.lang.scala._

import scala.concurrent.duration._
import scala.collection.JavaConverters._

object Observerables {

  def hello(names: String*) {
    Observable.from(names) subscribe { n =>
      println(s"Hello $n!")
    }
  }

  def ticks(): Unit = {
    val ticks: Observable[Long] = Observable.interval(1 seconds)
    val evens: Observable[Long] = ticks.filter(s => s % 2 == 0)

    val bugs: Observable[Seq[Long]] = ticks.slidingBuffer(4, 1)
    val s = bugs.subscribe(b => println(b))

    s.unsubscribe()
  }

  def pullExample(): Unit = {
    val list = List("Java", "C", "C++", "PHP", "Go")
    list.foreach(println(_))
  }

  def pushExample(): Unit = {
    val list = List("Java", "C", "C++", "PHP", "Go")
    //Observable from iterator
    val observable: Observable[String] = Observable.from(list);

    // subscribe(onNext: String => Unit, onError: Throwable => Unit, onCompleted: () => Unit): Subscription
    val subscription = observable.subscribe({ n =>
      println(n)
    }, { e =>
      e.printStackTrace()
    }, { () =>
      println("Unsubscribed now")
    }
    )
    Thread.sleep(2000)
    subscription.unsubscribe()
  }

  def fromObserver(): Unit = {
    Observable
  }

  def main(args: Array[String]): Unit = {
    hello("Joe", "John", "Frank")
    println("\n----- Push Example -----")
    pushExample()
  }

//  def apply[T](error: Throwable): Observable[T] = {
//    Observable[T](observer => {
//      observer.onError(error)
//      Subscription {}
//    }
//    )
//  }
}

// libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.5"

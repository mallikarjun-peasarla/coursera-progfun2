package eg.coursera.week4.signal1

import scala.util.DynamicVariable

/**
  * First Simple implementation of Signal
  */

class Signal[T](expr: => T) {

  import Signal._

  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set.empty

  update(expr)

  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr())

    //re-evaluate callers
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set.empty
      obs.foreach(_.computeValue())
    }
  }

  def apply(): T = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }

}

//Race conditions can result, if we try to evaluate several signal expressions in parallel
//The caller signal will become “garbled” by concurrent updates.
object Signal {
  // This makes use of the worst kind of state: global state.
  private val caller = new StackableVariable[Signal[_]](NoSignal)

  def apply[T](expr: => T): Signal[T] = new Signal(expr)
}

object NoSignal extends Signal[Nothing](???) {
  override protected def computeValue(): Unit = ()
}

class Var[T](expr: => T) extends Signal[T](expr) {
  override def update(expr: => T): Unit = super.update(expr)
}

object Var {
  def apply[T](expr: => T): Var[T] = new Var(expr)
}

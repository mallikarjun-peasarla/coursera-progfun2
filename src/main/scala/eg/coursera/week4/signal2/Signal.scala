package eg.coursera.week4.signal2

import scala.util.DynamicVariable

/**
  * Created by adrienlamit on 11/02/17.
  */

class Signal[T](expr: => T) {

  import Signal._

  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()

  update(expr)

  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  protected def computeValue(): Unit = {

    // What happens here is that :
    // - this is added to the callers stack if myExpr() is of type Signal[_]
    //     then this is added to myExpr()'s observers because myExpr().apply()
    //     is called with this on top of the caller stack.
    //     Stack is useful for complex dependencies.
    // - if myExpr() is e.g a Int literal
    //     then basically myExpr()'s observers will add nothing because stack is empty
    //
    // See class StackableVariable[T]'s implementation to convince yourself.
    val newValue = caller.withValue(this)(myExpr())

    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set.empty
      obs foreach (_.computeValue())
    }
  }

  def apply(): T = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }

}

/*
One way to get around the problem of concurrent accesses to global state StackVariable, is to use synchronization.
But this blocks threads, can be slow, and can lead to deadlocks.

Another solution is to replace global state by thread-local state.
  .Thread-local state means that each thread accesses a separate copy of a variable.
  .It is supported in Scala through class scala.util.DynamicVariable .
 */
object Signal {
  /*
  DynamicVariables provide a binding mechanism where the current value is found through dynamic scope,
  but where access to the variable itself is resolved through static scope. This is implementation of java InheritableThreadLocal

  When a new thread is created, the DynamicVariable gets a copy of the stack of bindings from the parent thread,
  and from then on the bindings for the new thread are independent of those for the original thread.
   */
  private val caller = new DynamicVariable[Signal[_]](NoSignal)

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

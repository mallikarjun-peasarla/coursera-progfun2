import eg.coursera.week4.signal2.BankAccountSignal2.consolidated
import eg.coursera.week4.signal2.{BankAccount, Signal, Var}

val a = new BankAccount
val b = new BankAccount
val c = consolidated(List(a, b))

c()

a deposit 20

c()

val v = Var(0)
val x = Signal(v())
x()
v() = 2
x()

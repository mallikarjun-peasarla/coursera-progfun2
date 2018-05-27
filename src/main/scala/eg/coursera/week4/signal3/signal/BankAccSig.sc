import eg.coursera.week4.signal3.BankAccount
import eg.coursera.week4.signal3.BankAccountSignal.consolidated
import eg.coursera.week4.signal3.Signal

val a = new BankAccount()
val b = new BankAccount()
val c = consolidated(List(a, b))
c()
a deposit 20
c()
b deposit 30
c()
val xchange = Signal(246.00)
val inDollar = Signal(c() * xchange())
inDollar()
b withdraw 10
inDollar()
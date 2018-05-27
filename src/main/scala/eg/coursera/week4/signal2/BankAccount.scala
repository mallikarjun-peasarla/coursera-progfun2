package eg.coursera.week4.signal2

class BankAccount {

  var balance = Var(0)

  def deposit(amount: Int): Unit = {
    if (amount > 0) {
      val b = balance()
      balance() = b + amount
    }
  }

  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance()) {
      val b = balance()
      balance() = b - amount
    } else throw new Error("insufficient funds")
  }

}

object BankAccountSignal2 {

  def consolidated(accts: List[BankAccount]): Signal[Int] = {
    Signal(accts.map(_.balance()).sum)
  }

  def main(args: Array[String]): Unit = {
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
  }
}
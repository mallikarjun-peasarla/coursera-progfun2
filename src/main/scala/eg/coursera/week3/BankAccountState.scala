package eg.coursera.week3

class BankAccount {
  private var balance = 0
  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
  }
  def withdraw(amount: Int): Int =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("insufficient funds")
}

object BankAccountState {
  def main(args: Array[String]): Unit = {
    val account = new BankAccount
    val balance = account deposit 50
    val result1 = account withdraw 20
    val result2 = account withdraw 20
    //each result is different because there is state in bank account which is different during each call.
  }
}

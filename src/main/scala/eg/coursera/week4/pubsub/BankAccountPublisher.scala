package eg.coursera.week4.pubsub

class BankAccount extends Publisher {
  //we publish everytime we change the state of the bank account
  private var balance = 0

  //accessor method
  def currentBalance = balance

  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
    publish()            // <--
  }

  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish()          // <--
    } else throw new Error("insufficient funds")
  }

}

object BankAccountPublisher {
  def main(args: Array[String]): Unit = {
    val account1 = new BankAccount
    val account2 = new BankAccount
    val c = new Consolidator(List(account1, account2))
    account1 deposit 50
    account1 withdraw 20
    account2 deposit 30

    println(c.totalBalance)
    //each result is different because there is state in bank account which is different during each call.
  }
}

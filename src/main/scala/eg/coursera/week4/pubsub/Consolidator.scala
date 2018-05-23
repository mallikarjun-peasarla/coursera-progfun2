package eg.coursera.week4.pubsub

// A Subscriber to maintain the total balance of a list of accounts
class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach(_.subscribe(this)) //subscribe to all BankAccounts

  private var total: Int = _
  compute()

  private def compute() = {
    total = observed.map(_.currentBalance).sum
  }
  //everytime something changes, you compute
  def handler(pub: Publisher) = compute()
  def totalBalance = total
}
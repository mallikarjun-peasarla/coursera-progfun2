import eg.coursera.week4.pubsub.{BankAccount, Consolidator}

val account1 = new BankAccount
val account2 = new BankAccount
val c = new Consolidator(List(account1, account2))

c.totalBalance

account1 deposit 50
account1 withdraw 20
account2 deposit 35

c.totalBalance

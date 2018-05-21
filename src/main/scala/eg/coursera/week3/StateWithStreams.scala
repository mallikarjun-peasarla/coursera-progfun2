package eg.coursera.week3

object StateWithStreams {
  def cons[T](hd: T, tl: => Stream[T]) = new MyStream[T] {
    def head: T = hd
    private var tlOpt: Option[Stream[T]] = None
    def tail: Stream[T] = tlOpt match {
      case Some(x: Stream[T]) => x
      case None => tlOpt = Some(tl); tail
    }
  }

//  def cons2[T](hd: T, tl: => Stream[T]) = new Stream[T] {
//    override def head = hd
//    private var tlOpt: Option[Stream[T]] = None
//    override def tail: T = tlOpt match {
//      case Some(x: T) => x
//      case None => tlOpt = Some(tl); tail
//    }
//  }

  def main(args: Array[String]): Unit = {
    val account1 = new BankAccount
    val stream1 = cons[BankAccount](account1, Stream[BankAccount]())
    println(stream1.head)
    println(stream1.tail)

    println("----------")
    val account2 = new BankAccount
    account2 deposit  200
    val stream2 = cons[BankAccount](account1, Stream[BankAccount](account2))
    println(stream2.head)
    println(stream2.tail)
    println("----------")
    stream2.head.deposit(100)
    println(stream2.head)
    println(stream2.tail)
  }

}

trait MyStream[T] {

}
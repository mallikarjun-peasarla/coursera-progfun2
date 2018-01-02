package eg.coursera.reactivecheatsheet

trait DataStore[+T] { self =>
  def add[T](t: T): DataStore[T]
  def get[T](n: Int): T

//  def map[S](f: T => S) : DataStore[S] = new DataStore[S] {
//    def add[T](t: T) = self.add(t)
//    def get[T](n: Int): T = self.get(n)
//  }
  def map[S](f: T => S) : DataStore[S]
  def flatmap[S](f: T => DataStore[S]) : DataStore[S]
}

class DataStoreImpl[T](val data: Seq[T]) extends  DataStore[T] {
  def add[T](element: T): DataStoreImpl[T] = {
    val x = data :+ element
    new DataStoreImpl[T](x.asInstanceOf[Seq[T]])
  }
  def get[T](n: Int): T = {
    val x = data.toList
    x(n).asInstanceOf[T]
  }
  def map[S](f: T => S): DataStore[S] = {
    new DataStoreImpl(data.map(f(_)))
  }

  def flatmap[S](f: T => DataStore[S]): DataStore[S] = {
    val x = data.flatMap(f(_).asInstanceOf[DataStoreImpl[S]].data)
    new DataStoreImpl(x)
  }

  def filter(f: T => Boolean): DataStore[T] = new DataStoreImpl[T](data.filter(f(_)))

  override def toString: String = "DataStoreImpl["+data.toString()+"]"

}

case class Player(name: String, age: Int, position: String, club: String)
case class Person(name: String, age: Int)

object Monad {
  def main(args: Array[String]): Unit = {
    val p1 = Player("Joe", 28, "midf", "Chelsea")
    val p2 = Player("John", 29, "def", "Chelsea")
    val myPlayerStore = new DataStoreImpl[Player](Seq(p1, p2))
    val p3 = Player("Frank", 29, "midf", "Chelsea")
    val newPlayStore = myPlayerStore.add(p3)

    val personStore = myPlayerStore.map { player =>
      Person(player.name, player.age)
    }
    println(newPlayStore)
    println(personStore.get(0))
    println(personStore)

    // flatmap
    println("\n---- flatMap----")
    val midfStore = new DataStoreImpl[Player](Seq(p1, p3))
    val defStore = new DataStoreImpl[Player](Seq(p2))
    val allStores = new DataStoreImpl[DataStoreImpl[Player]](Seq(midfStore, defStore))
    val flattenPlayerStore = allStores.flatmap(s => s)
    val flattenPersonStore = allStores.flatmap(ps => ps.map(p => Person(p.name, p.age)))
    println(flattenPlayerStore)
    println(flattenPersonStore)

    // filter
    println("\n---- filter----")
    println(newPlayStore.filter(_.position == "def"))

    // for comprehension on DataStoreImpl
    println("\n---- for-comprehension----")
    val defenders = for {
      players <- playStore("def")
    } yield players
    println(defenders)
  }

  def playStore(pos: String): DataStore[Player] = {
    val p1 = Player("Joe", 28, "midf", "Chelsea")
    val p2 = Player("John", 29, "def", "Chelsea")
    val p3 = Player("Frank", 29, "midf", "Chelsea")
    val p4 = Player("Didier", 27, "forw", "Chelsea")
    val p5 = Player("Caesar", 29, "def", "Chelsea")
    val allPlayers = new DataStoreImpl[Player](Seq(p1, p2, p3, p4, p5))
    allPlayers.filter(_.position == pos)
  }

}

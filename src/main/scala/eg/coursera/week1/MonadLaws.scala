package eg.coursera.week1

class DataContainer[T](val data: Seq[T]) {      // not unit(x) - def unit[T](x: T): M[T]

  def map[S](f: T => S): DataContainer[S] = {
    new DataContainer(data.map(f(_)))
  }

  def flatmap[S](f: T => DataContainer[S]): DataContainer[S] = {
    new DataContainer(data.flatMap(f(_).data))
  }

  def filter(f: T => Boolean): DataContainer[T] = new DataContainer[T](data.filter(f(_)))

  override def toString: String = "DataContainer["+data.toString()+"]"

}

case class Employee(name: String, id: Int)
case class Person(name: String)

object MonadLaws {

  val f1: PartialFunction[Employee, String] = {
    case e: Employee => e.name
  }

  def f2(e: Employee): String = e.name

  def g2(e: Employee): DataContainer[Employee] = new DataContainer[Employee](Seq(e))

  def h2(e: Employee): DataContainer[String] = new DataContainer[String](Seq(e.name))

  def main(args: Array[String]): Unit = {
    val emp1 = Employee("e1", 101)
    val emp2 = Employee("e2", 102)
    val emp3 = Employee("e3", 103)
    val data = Seq(emp1, emp2, emp3)
    val container = new DataContainer[Employee](data)

    val dataMap = container.map(f1(_))         // m map f
    val dataMapCasc = container.map(e => new DataContainer(Seq(e)))
    //val dataMapCascFlatten = dataMapCasc.flatten
    val dataFlatMap = container.flatmap(e => new DataContainer(Seq(e)))

    // map ~ flatmap(x => unit(f(x)))
    // val dataMapEq = new DataContainer[String](dataFlatMap.data.map(f(_)))   // m flatmap
    val dataMapEq = container.flatmap(e => new DataContainer(Seq(f1(e))))
    assert(dataMap.toString == dataMapEq.toString)

    // 1. Associativity - m flatMap f flatMap g == m flatMap (x => f(x) flatMap g)
    val dflat1 = container.flatmap(e => g2(e)).flatmap(d => h2(d))
    val dflat2 = container.flatmap(e => g2(e).flatmap(d => h2(d)))
    assert(dflat1 == dflat2)

    // 2. Left unit - unit(x) flatMap f == f(x)
    //                x: T, M[T] - M[U] ==
    val unitFlatMapG2 = container.flatmap(e => g2(e))
    //val dataOnG2 = g2(data)

    // 3. Right unit - m flatMap unit = m


  }
}
/*
	trait M[T] {
		def flatMap[U](f: T => M[U]): M[U]
	}
	def unit[T](x: T): M[T]					// constructor with type argument in scala x
 */
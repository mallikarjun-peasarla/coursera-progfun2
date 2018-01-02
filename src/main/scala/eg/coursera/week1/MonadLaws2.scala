package eg.coursera.week1

/*
	trait M[T] {
		def flatMap[U](f: T => M[U]): M[U]
	}
	def unit[T](x: T): M[T]					// constructor with type argument in scala x
 */

class ObjectWrapper[T](val item: T) {      // unit(x) - def unit[T](x: T): M[T]

  def map[S](f: T => S): ObjectWrapper[S] =  new ObjectWrapper(f(item))

  def flatmap[S](f: T => ObjectWrapper[S]): ObjectWrapper[S] = f(item)    // def flatMap[U](f: T => M[U]): M[U]

  // def filter(f: T => Boolean): DataContainer[T] = new DataContainer[T](data.filter(f(_)))
  override def toString: String = "DataContainer["+item.toString()+"]"
}

object MonadLaws2 {

  def f(e: Employee): Person = Person(e.name)
  def g(e: Employee): ObjectWrapper[Employee] = new ObjectWrapper[Employee](e)
  def h(e: Employee): ObjectWrapper[Person] = new ObjectWrapper[Person](f(e))

  def main(args: Array[String]): Unit = {
    val emp1 = Employee("e1", 101)
    val emp2 = Employee("e2", 102)
    val wrapper = new ObjectWrapper[Employee](emp1)

    val wrapperMap = wrapper.map(e => f(e))
    val wrapperFlatmapG = wrapper.flatmap(e => g(e))

    // map ~ flatmap(x => unit(f(x)))
    val wrapperMapEq = wrapper.flatmap(e => new ObjectWrapper[Person](f(e)))
    assert(wrapperMap.toString == wrapperMapEq.toString)

    // 1. Associativity - m flatMap f flatMap g == m flatMap (x => f(x) flatMap g)
    val dflat1 = wrapper.flatmap(e => g(e)).flatmap(d => h(d))
    val dflat2 = wrapper.flatmap(e => g(e).flatmap(d => h(d)))
    assert(dflat1.toString == dflat2.toString)

    // 2. Left unit - unit(x) flatMap f == f(x)
    val unitFlatMapH = wrapper.flatmap(e => h(e))
    val wrapperOfH = h(emp1)
    assert(unitFlatMapH.toString == wrapperOfH.toString)

    // 3. Right unit - m flatMap unit = m
    val monadFlatMapUnit = wrapper.flatmap(e => new ObjectWrapper[Employee](e))
    assert(monadFlatMapUnit.toString == wrapper.toString)

    val result = for (x <- wrapper) yield x
    assert(result.toString == wrapper.toString)
  }
}

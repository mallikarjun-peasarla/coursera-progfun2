package eg.coursera.cheatsheet

object Currying {
  // uncurried version (type is (Int, Int) => Int)
  def f1(discount: Int, amount: Double): Double = amount - discount;
  def f2(discount: Int)(amount: Double): Double = amount - discount;

  def calculate(rates: List[Int], constDiscount:Double => Double) = {
    rates.map(constDiscount(_))
  }

  def main(args: Array[String]): Unit = {
    val rates = List(50, 90, 30)
    val slashedRates1 = rates.map(f1(5, _))
    val slashedRates2 = calculate(rates, f2(5))
    assert(slashedRates1 == slashedRates2)

    val discount5On: Function1[Double, Double] = f2(5)
    val anotherDef: (Double => Double) = f2(5)
    val afterDiscount = discount5On(30)
    val afterDiscountAgain = anotherDef(30)
    println(afterDiscountAgain)
  }
}

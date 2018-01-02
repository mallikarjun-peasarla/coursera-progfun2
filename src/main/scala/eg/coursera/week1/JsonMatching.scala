package eg.coursera.week1

object JsonMatching {

  def show(obj: JSON): String = obj match {

    case JSeq(elems) =>
      "[" + (elems map show mkString ", ") + "]"

    case JObj(bindings) =>
      "{" + (bindings map {
        case (key, value) => "\"" + key + "\"" + ": " + show(value) // function type is JBinding => String.
      } mkString ", ") + "}"
    /* type JBinding = (String, JSON)
      JBinding => String is shorthand for scala.Function1[JBinding, String]
      where scala.Function1 is a trait defined in the standard library
    */

    case JNum(num) => num.toString
    case JStr(str) => "\"" + str + "\""
    case JBool(b) => b.toString
    case JNull => "null"
  }

  def main(args: Array[String]): Unit = {
    val data = CommonData.personJson1

    println(show(data))
  }

}

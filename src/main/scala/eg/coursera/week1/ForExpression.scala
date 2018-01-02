package eg.coursera.week1

object ForExpression {

  def main(args: Array[String]): Unit = {

    val data: List[JSON] = List(CommonData.personJson1)
    val r = for {
      JObj(bindings) <- data
      JSeq(phones) = bindings("phoneNumbers")
      JObj(phone) <- phones
      JStr(digits) = phone("number")
      if digits startsWith "212"
    } yield (bindings("firstName"), bindings("lastName"))

    println(r)
  }

}

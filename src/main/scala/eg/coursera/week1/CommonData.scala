package eg.coursera.week1

object CommonData {

  val personJson1 = new JObj(Map(
    "firstName" -> JStr("John"),
    "lastName" -> JStr("Smith"),
    "address" -> JObj(Map(
      "streetAddress" -> JStr("21 2nd Street"),
      "state" -> JStr("NY"),
      "postalCode" -> JNum(10021)
    )),
    "phoneNumbers" -> JSeq(List(
      JObj(Map(
        "type" -> JStr("home"),"number" -> JStr("212 555-1234")
      )),
      JObj(Map(
        "type" -> JStr("fax"),"number" -> JStr("646 555-4567")
      ))
    ))
  ))
}

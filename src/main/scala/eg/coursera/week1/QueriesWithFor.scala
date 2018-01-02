package eg.coursera.week1

case class Book(title: String, authors: List[String])

object QueriesWithFor {

  val books: List[Book] = List(
    Book(title = "Structure and Interpretation of Computer Programs",
      authors = List("Abelson, Harald", "Sussman, Gerald J.")),
    Book(title = "Introduction to Functional Programming",
      authors = List("Bird, Richard", "Wadler, Phil")),
    Book(title = "Effective Java",
      authors = List("Bloch, Joshua")),
    Book(title = "Effective Java 2",
      authors = List("Bloch, Joshua")),
    Book(title = "Java Puzzlers",
      authors = List("Bloch, Joshua", "Gafter, Neal")),
    Book(title = "Programming in Scala",
      authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")))

  def main(args: Array[String]): Unit = {
    // find the titles of books whose author’s name is "Bird"
    val res1 =
      for (b <- books; a <- b.authors if a startsWith "Bird,")
      yield b.title   // List(Introduction to Functional Programming)

    // find all the books which have the word "Program" in the title
    val res2 =
      for (b <- books if b.title.indexOf("Program") >= 0)
      yield b.title   // List(Structure and Interpretation of Computer Programs, Introduction to Functional Programming, Programming in Scala)

    val res3 = for {
      b1 <- books
      b2 <- books
      if b1 != b2     // b1.title < b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1        // List(Bloch, Joshua, Bloch, Joshua)

    // get authors who have written more than two books
    val res4 = for {
      b1 <- books
      b2 <- books
      if b1.title < b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1

    // remove duplicates from above
    val res5 = { for {
      b1 <- books
      b2 <- books
      if b1.title < b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1
    }.distinct

    // better query
    val bookSet = books.toSet
    val res6 = for {
      b1 <- bookSet
      b2 <- bookSet
      if b1 != b2
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1

  }
}

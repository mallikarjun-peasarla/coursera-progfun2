package eg.coursera.week3

object AssignmentDscrt {
  lazy val func = "az"
  def main(args: Array[String]): Unit = {
    func("anything") = someFunction("something")
  }

  def someFunction(str: String) = "random"

}

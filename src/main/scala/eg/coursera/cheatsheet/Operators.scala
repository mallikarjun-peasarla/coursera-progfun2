package eg.coursera.cheatsheet

object Operators {
  val year1 = Score(1347, 5, 1)
  val year2 = Score(1123, 4, 2)
  val total = year1 + year2
}

case class Score(totalRuns: Int, halfCenturies: Int, centuries: Int) {
  def add(score: Score): Score = Score(score.totalRuns + this.totalRuns,
    score.halfCenturies + this.halfCenturies, score.centuries + this.centuries)
  def +(score: Score): Score = add(score)
}
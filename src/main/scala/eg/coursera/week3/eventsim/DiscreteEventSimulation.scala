package eg.coursera.week3.eventsim

object DiscreteEventSimulation extends Circuits with Parameters {
  def main(args: Array[String]): Unit = {
    val in1, in2, sum, carry = new Wire
    halfAdder(in1, in2, sum, carry)
    // design Q: why a probe and run? I would have designed halfAdder giving output directly.
    probe("sum", sum)
    probe("carry", carry)

    in1 setSignal true
    run()

    in2 setSignal true
    run()

    in1 setSignal false
    run()
  }
}

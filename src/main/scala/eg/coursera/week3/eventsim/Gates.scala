package eg.coursera.week3.eventsim

abstract class Gates extends Simulation {

  //design to-do. why these are declared abstract instead of directly extending gates class with Parameters trait
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int

  def inverter(input: Wire, output: Wire): Unit = {
    def invertAction(): Unit = {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) {
        output setSignal !inputSig
      }
    }

    input addAction invertAction
  }

  def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def andAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(AndGateDelay) {
        output setSignal (in1Sig & in2Sig)
      }
    }
    in1 addAction andAction
    in2 addAction andAction
  }

  def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(OrGateDelay) {
        output setSignal (in1Sig | in2Sig)
      }
    }
    in1 addAction orAction
    in2 addAction orAction
  }

  def orGate2(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      afterDelay(OrGateDelay) {
        output setSignal (in1.getSignal | in2.getSignal) }
    }
    in1 addAction orAction
    in2 addAction orAction
  }

  /*
  Q: What would change in the circuit simulation if the implementation of orGateAlt was used for OR?
  A: The times are different, and orGateAlt may also produce additional events.
   */
  def orGateAlt(in1: Wire, in2: Wire, output: Wire): Unit = {
    val notIn1, notIn2, notOut = new Wire
    inverter(in1, notIn1); inverter(in2, notIn2)
    andGate(notIn1, notIn2, notOut)
    inverter(notOut, output)
  }

  def probe(name: String, wire: Wire): Unit = {
    def probeAction(): Unit = {
      println(s"$name ${System.currentTimeMillis()} value = ${wire.getSignal}")
    }
    wire addAction probeAction
  }

  class Wire extends Simulation {
    private var sigVal = false
    private var actions: List[Action] = Nil
    def getSignal: Boolean = sigVal
    def setSignal(s: Boolean): Unit =
      if (s != sigVal) {
        sigVal = s
        actions foreach (_())
      }
    def addAction(a: Action): Unit = {
      actions = a :: actions
      a()
    }
  }

}

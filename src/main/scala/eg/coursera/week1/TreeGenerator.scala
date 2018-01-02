package eg.coursera.week1

import eg.coursera.reactivecheatsheet.GeneratorWithFor.{pairs, test}

trait Generator[+T] { self =>
  def generate: T
  def map[S](f: T => S) : Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }
  def flatMap[S](f: T => Generator[S]) : Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}

trait Tree
case class Inner(left: Tree, right: Tree) extends Tree
case class Leaf(x: Int) extends Tree

object TreeGenerator {
  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }
  val booleans = for {x <- integers} yield x > 0

  def leafGen = for {x <- integers} yield Leaf(x)
  def nodeGen = for {
    leftLeaf <- treeGen
    rightLeaf <- treeGen
  } yield Inner(leftLeaf, rightLeaf)
  val treeGen: Generator[Tree] = for {
    isLeaf <- booleans                      // This can be improved, by decreasing probability of single node from .5 to .25 choosing from 1-4 and 1 being a leaf
    tree <- if(isLeaf) leafGen else nodeGen
  } yield tree

  def main(args: Array[String]): Unit = {
    test(integers) ({                // scalacheck is a better tool test generators
      case i => i >= Integer.MIN_VALUE && i <= Integer.MAX_VALUE
    })

    println(treeGen.generate)

    //test with scalacheck
    import org.scalacheck.Prop.forAll
    forAll { (l1: List[Int], l2: List[Int]) =>
      l1.size + l2.size == (l1 ++ l2).size
    }

    //test tree with scalacheck
    def testTree(tree: Tree): Boolean = tree match {
      case node: Inner => testTree(node.left) && testTree(node.right)
      case leaf: Leaf => Integer.MIN_VALUE <= leaf.x
    }
//    forAll { tree: Generator[Tree] =>
//      testTree(tree.generate)
//    }
  }

  def test[T](g: Generator[T], numTimes: Int = 100)
             (test: T => Boolean): Unit = {
    for (i <- 0 until numTimes) {
      val value = g.generate
      assert(test(value), "test failed for "+value)
    }
    println("passed "+numTimes+" tests")
  }
}

name := "coursera-progfun2"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-async" % "0.9.6"
libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.5"                 // added for observables
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5" // added for testing generators

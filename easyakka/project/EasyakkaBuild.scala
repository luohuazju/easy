import sbt._
import sbt.Keys._

object EasyakkaBuild extends Build {

  lazy val easyakka = Project(
    id = "easyakka",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "easyakka",
      organization := "com.sillycat.easyakka",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.1"
    )
  )
}

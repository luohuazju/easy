import sbt._
import Keys._

object ApplicationBuild extends Build {

  lazy val main = Project(id = "easygatling",
    base = file("."), settings = Project.defaultSettings).settings()

}


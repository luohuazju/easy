import AssemblyKeys._

name := "easycassandraserver" 

organization := "com.sillycat" 

version := "1.0" 

scalaVersion := "2.9.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8") 

resolvers ++= Seq(
	"sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  	"sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  	"typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/",
  	"spray repo"         at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
    "ch.qos.logback"      %   "logback-classic"           % "1.0.3"
)

seq(Revolver.settings: _*)




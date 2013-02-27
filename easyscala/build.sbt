organization  := "com.sillycat"

name		  := "easyscala"

version       := "1.0"

scalaVersion in ThisBuild := "2.10.0"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "sonatype releases"   at "https://oss.sonatype.org/content/repositories/releases/",
  "sonatype snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe repo"       at "http://repo.typesafe.com/typesafe/releases/",
  "spray repo"          at "http://repo.spray.io/",
  "sbt-plugin-releases" at "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"
)

libraryDependencies ++= Seq(
  "joda-time"			%  "joda-time"					% "2.1",
  "org.joda" 			%  "joda-convert" 				% "1.3"
)


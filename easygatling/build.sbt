import AssemblyKeys._

name := "easygatling" 

organization := "com.sillycat" 

version := "1.0" 

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8") 

resolvers ++= Seq(
	"sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  	"sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  	"typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/",
  	"spray repo"         at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
  	"com.typesafe"              %   "config"                    % "1.0.0",
    "com.excilys.ebi.gatling"   %   "gatling-app"               % "1.4.5",
    "com.excilys.ebi.gatling"   %   "gatling-recorder"          % "1.4.5",
    "com.excilys.ebi.gatling.highcharts" % "gatling-charts-highcharts" % "1.4.5",
    "ch.qos.logback"            % "logback-classic"             % "1.0.0",
    "log4j"                     % "log4j"                       % "1.2.14"
)

seq(Revolver.settings: _*)

publishArtifact in Test := true

assemblySettings

mainClass in assembly := Some("com.sillycat.easygatling.Boot")

artifact in (Compile, assembly) ~= { art =>
  art.copy(`classifier` = Some("assembly"))
}

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  cp filter {_.data.getName == "parboiled-scala_2.10.0-RC1-1.1.3.jar"}
}

addArtifact(artifact in (Compile, assembly), assembly)

scalariformSettings


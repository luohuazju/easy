import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

name := "easydisruptor" 

organization := "com.sillycat" 

version := "1.0" 

scalaVersion := "2.10.0"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8") 

resolvers ++= Seq(
 Resolver.defaultLocal,
 "MAVEN repo"        at "http://repo1.maven.org/maven2",
 "sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/",
 "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
 "typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/",
 "spray repo"         at "http://repo.spray.io/",
 "Spray repo second"  at "http://repo.spray.cc/",
 "Akka repo"          at "http://repo.akka.io/releases/"
)

libraryDependencies ++= Seq(
    "org.scalatest"       %   "scalatest_2.10"            % "1.9.1"   % "test",
    "org.specs2"          %%  "specs2"                    % "1.13"    % "test",
    "com.lmax"            %   "disruptor"                 % "3.3.0"
)

seq(Revolver.settings: _*)

seq(assemblySettings: _*)

mainClass in assembly := Some("com.sillycat.easydisruptor.ExecutorApp")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "jasper", xs @ _*) => MergeStrategy.first
    case PathList("org", "fusesource", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "commons", "beanutils", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "commons", "collections", xs @ _*) => MergeStrategy.first
    case PathList("org", "eclipse", xs @ _*) => MergeStrategy.first
    case PathList("META-INF", xs @ _*) =>
      (xs map {_.toLowerCase}) match {
            case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
              MergeStrategy.discard
            case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
              MergeStrategy.discard
            case "plexus" :: xs =>
              MergeStrategy.discard
            case "services" :: xs =>
              MergeStrategy.filterDistinctLines
            case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
              MergeStrategy.filterDistinctLines
            case ps @ (x :: xs) if ps.last.endsWith(".jnilib") || ps.last.endsWith(".dll") =>
              MergeStrategy.first
            case ps @ (x :: xs) if ps.last.endsWith(".txt") =>
              MergeStrategy.discard
            case ("notice" :: Nil) | ("license" :: Nil)=>
              MergeStrategy.discard
            case _ => MergeStrategy.deduplicate
      }
    case "application.conf" => MergeStrategy.concat
    case "about.html" => MergeStrategy.discard
    case "plugin.properties" => MergeStrategy.first
    case x => old(x)
  }
}

artifact in (Compile, assembly) ~= { art =>
  art.copy(`classifier` = Some("assembly"))
}

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  cp filter {_.data.getName == "parboiled-scala_2.10.0-RC1-1.1.3.jar"}
}

addArtifact(artifact in (Compile, assembly), assembly)




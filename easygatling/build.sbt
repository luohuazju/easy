import sbtassembly.Plugin._
import sbtassembly.Plugin.AssemblyKeys._
import scala.Some
import scala.Some
import scala.Some
import scala.Some

name := "easygatling"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

fork in test := true

javaOptions += "-Xmx4G"

mainClass in Compile := Some("com.sillycat.easygatling.Engine")

seq(assemblySettings: _*)

mainClass in assembly := Some("com.sillycat.easygatling.Engine")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case "application.conf" => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil)  =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".txt") =>
        MergeStrategy.discard
      case ("license" :: Nil)=>
        MergeStrategy.discard
      case ("notice" :: Nil) =>
        MergeStrategy.discard
      case _ => MergeStrategy.first
    }
  case x => MergeStrategy.first
}
}

artifact in (Compile, assembly) ~= { art =>
  art.copy(`classifier` = Some("assembly"))
}

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  cp filter {_.data.getName == "minlog-1.2.jar" }
}

addArtifact(artifact in (Compile, assembly), assembly)

test in assembly := {}
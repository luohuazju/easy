
name := "easyspray" 

organization := "com.sillycat" 

version := "1.0" 

scalaVersion := "2.10.0-RC1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8") 

resolvers ++= Seq(
	"sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  	"sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  	"typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/",
  	"spray repo"         at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
	"io.spray"            %   "spray-io"                  % "1.1-M7",
	"io.spray"            %   "spray-can"                 % "1.1-M7",
	"io.spray"            %   "spray-http"                % "1.1-M7",
	"io.spray"            %   "spray-util"                % "1.1-M7",
	"com.typesafe.akka"   %%  "akka-actor"                % "2.1.0-RC1"   cross CrossVersion.full,
  	"com.typesafe"        %   "config"                    % "1.0.0"
)

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
	"io.spray"            %   "spray-routing"             % "1.1-M7",
	"io.spray"            %   "spray-http"                % "1.1-M7",
	"io.spray"            %   "spray-util"                % "1.1-M7",
	"com.typesafe.akka"   %%  "akka-actor"                % "2.1.0-RC1"   cross CrossVersion.full,
  	"com.typesafe"        %   "config"                    % "1.0.0",
  	"com.typesafe"        %   "slick_2.10.0-RC2"      	  % "0.11.2",
  	"org.slf4j" 		  %   "slf4j-nop" 				  % "1.6.4",
  	"com.h2database" 	  %   "h2" 						  % "1.3.170",
  	"io.spray"            %   "spray-json_2.10"           % "1.2.3",
  	"org.xerial" 		  %   "sqlite-jdbc" 			  % "3.6.20",
  	"mysql" 		      %   "mysql-connector-java" 	  % "5.1.13"
	/*
  		"org.apache.derby" % "derby" % "10.6.1.0",
  		"org.hsqldb" % "hsqldb" % "2.0.0",
  		"postgresql" % "postgresql" % "8.4-701.jdbc4",
  		"mysql" % "mysql-connector-java" % "5.1.13"
	*/
)
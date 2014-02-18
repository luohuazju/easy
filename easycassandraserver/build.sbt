import AssemblyKeys._

name := "easycassandraserver" 

organization := "com.sillycat" 

version := "1.0" 

scalaVersion  := "2.10.0"

crossScalaVersions := Seq("2.9.2","2.10.0","2.10.1")

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
	  "io.spray"            %   "spray-caching"             % "1.1-M7",
	  "io.spray"            %   "spray-http"                % "1.1-M7",
	  "io.spray" 	          %   "spray-testkit" 	          % "1.1-M7",
	  "io.spray"            %   "spray-util"                % "1.1-M7",
    "com.typesafe.akka"   %%  "akka-actor"                % "2.1.0",
    "com.typesafe.akka"   %%  "akka-testkit"              % "2.1.0",
    "com.typesafe.akka"   %%  "akka-transactor"           % "2.1.0",
  	"com.typesafe"        %   "config"                    % "1.0.0",
  	"com.typesafe"        %   "slick_2.10.0-RC2"          % "0.11.2",
  	//"org.slf4j" 	      %   "slf4j-nop" 	              % "1.6.4",
  	"com.h2database"      %   "h2" 			              % "1.3.170",
  	"io.spray"            %   "spray-json_2.10"           % "1.2.3",
  	"org.xerial" 	      %   "sqlite-jdbc" 	          % "3.6.20",
  	"mysql" 	          %   "mysql-connector-java"      % "5.1.13",
  	"joda-time"			  %   "joda-time"				  % "2.2",
  	"org.joda" 	 	  	  %   "joda-convert" 			  % "1.3.1",
  	"org.scalatest"       %   "scalatest_2.10"            % "1.9.1"   % "test",
    "org.specs2"          %%  "specs2"                    % "1.13"    % "test",
    "c3p0"                %   "c3p0"                      % "0.9.1.2",
    "com.typesafe"		  %%  "scalalogging-slf4j"	      % "1.0.1",
    "ch.qos.logback"      %   "logback-classic"           % "1.0.3",
    "org.hectorclient"    %   "hector-core"               % "1.1-3",
    "com.google.guava"    %   "guava"                     % "14.0.1",
    "com.google.code.findbugs" % "jsr305"                 % "2.0.1",
    "com.esotericsoftware.kryo" % "kryo"                  % "2.21",
    "com.datastax.cassandra" % "cassandra-driver-core" % "2.0.0-rc2"
)

seq(Revolver.settings: _*)




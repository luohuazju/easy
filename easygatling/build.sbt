

name := "easygatling"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

fork in test := true

javaOptions += "-Xmx4G"

mainClass in Compile := Some("com.sillycat.easygatling.Engine")


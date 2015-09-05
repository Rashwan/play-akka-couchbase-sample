name := """Play2APP"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

resolvers += "Local Maven Repository" at "file:///home/rashwan/.m2/repository"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.rashwan.akka.messages" % "akka-messenger" % "1.0-SNAPSHOT",
  "com.typesafe.akka" % "akka-remote_2.11" % "2.3.13"
)

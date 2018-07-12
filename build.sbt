import sbt.Resolver

name := "invite-homework"

version := "0.1"

scalaVersion := "2.12.6"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % Runtime,
  // Log dependencies
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "de.siegmar" % "logback-gelf" % "1.0.4",
  // Test dependencies
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.7"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case x => MergeStrategy.first
}

scalacOptions := Seq("-unchecked", "-deprecation")

assemblyJarName in assembly := "invite-homework.jar"
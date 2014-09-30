name := "loglist-importer"

version := "1.0-SNAPSHOT"

mainClass in (Compile, run) := Some("me.fornever.loglistimporter.Application")

scalaVersion := "2.11.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "mysql" % "mysql-connector-java" % "5.1.32",
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc41"
)
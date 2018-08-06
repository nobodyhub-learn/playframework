name := "MyPlayFramework"

version := "0.1"

scalaVersion := "2.12.6"

lazy val `MyPlayFramework` = project.in(file(".")).enablePlugins(PlayScala)

libraryDependencies += guice
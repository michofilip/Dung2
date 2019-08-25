name := "Dung2"

version := "0.1"

scalaVersion := "2.12.9"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test

lazy val Value = project
        .settings(
            libraryDependencies ++= Dependencies.valueDependencies
        )

lazy val Core = project
        .settings(
            libraryDependencies ++= Dependencies.coreDependencies
        )
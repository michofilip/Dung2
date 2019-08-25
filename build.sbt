name := "Dung2"

version := "0.1"

scalaVersion := "2.12.9"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test

lazy val commonSettings = Seq(
    //    organization := "com.example",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.9"
)

lazy val Value = project
        .settings(
            commonSettings,
            libraryDependencies ++= Dependencies.valueDependencies
        )

lazy val Core = project
        .settings(
            commonSettings,
            libraryDependencies ++= Dependencies.coreDependencies
        )
        .dependsOn(
            Value
        )
name := "Dung2"

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
        .dependsOn(Value)
        .settings(
            commonSettings,
            libraryDependencies ++= Dependencies.coreDependencies
        )

lazy val Old = project
        .settings(
            commonSettings,
            libraryDependencies ++= Dependencies.coreDependencies
        )
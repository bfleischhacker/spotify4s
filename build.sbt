
lazy val commonSettings = Seq(
  organization := "com.spotify4s",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)


lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "spotify4s"
  )

lazy val models = (project in file("models")).
  settings(commonSettings: _*).
  settings(
    name := "spotify4s-models",
    libraryDependencies ++= Seq(
      scalaTest
    )
  )

lazy val core = (project in file("core")).
  settings(commonSettings: _*).
  settings(
    name := "spotify4s-core",
    libraryDependencies ++= Seq(
      scalaTest
    )
  )

lazy val generation = (project in file("generation")).
  settings(commonSettings: _*).
  settings(
    name := "spotify4s-generation",
    libraryDependencies ++= Seq(
      "org.yaml" % "snakeyaml" % "1.16"
    )
  )

//lazy val upickle = "com.lihaoyi" %% "upickle" % "0.3.8"

lazy val scalactic = "org.scalactic" %% "scalactic" % "3.0.0-M15"
lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.0-M15" % "test"

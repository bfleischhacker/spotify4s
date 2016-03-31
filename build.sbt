
lazy val commonSettings = Seq(
  organization := "com.spotify4s",
  version := "0.1.0",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq(
    "-encoding", "UTF-8"
    , "-feature" // Emit warning and location for usages of features that should be imported explicitly
    , "-language:implicitConversions", "-language:higherKinds", "-language:existentials", "-language:postfixOps"
    , "-Xfatal-warnings" // Fail the compilation if there are any warnings
    , "-Xlint:_,-nullary-unit" // Enable or disable specific warnings (see list below)
    , "-Ywarn-dead-code" // Warn when dead code is identified
    , "-Ywarn-unused" // Warn when local and private vals, vars, defs, and types are are unused
    , "-Ywarn-unused-import" // Warn when imports are unused
    , "-deprecation"
  ),
  // Improved incremental compilation
  incOptions := incOptions.value.withNameHashing(true),
  // Improved dependency management
  updateOptions := updateOptions.value.withCachedResolution(true)
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
    libraryDependencies ++= Seq(scalaTest, cats) ++ circe
  )

lazy val core = (project in file("core")).
  settings(commonSettings: _*).
  settings(
    name := "spotify4s-core",
    libraryDependencies ++= Seq(scalaTest) ++ circe
  ).dependsOn(models)

lazy val coreScalaj = (project in file("scalaj")).
  settings(commonSettings: _*).
  settings(
    name := "spotify4s-scalaj",
    libraryDependencies ++= Seq(scalaTest, cats, scalaj) ++ circe
  ).dependsOn(models, core)

lazy val generation = (project in file("generation")).
  settings(commonSettings: _*).
  settings(
    name := "spotify4s-generation",
    libraryDependencies ++= Seq(
      "org.yaml" % "snakeyaml" % "1.16"
    )
  )

lazy val circeVersion = "0.3.0"

lazy val circe = Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion)

lazy val cats = "org.typelevel" %% "cats" % "0.4.0"

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.0-M15" % "test"

lazy val scalaj = "org.scalaj" %% "scalaj-http" % "2.2.1"

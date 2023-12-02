ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.5"

val streams = List(
  "co.fs2" %% "fs2-core" % "3.9.2",
    "co.fs2" %% "fs2-io" % "3.9.2"
)
val betterMonadicPlugin = compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
val tests= List(
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test,
  "org.scalatestplus" %% "mockito-4-11" % "3.2.17.0" % Test,
  "org.scalactic" %% "scalactic" % "3.2.17",
  "org.scalatest" %% "scalatest" % "3.2.17" % Test
)
lazy val root = (project in file(".")).settings(
  name := "advent-of-code-2023",
  libraryDependencies ++=  betterMonadicPlugin :: streams ++ tests
  )

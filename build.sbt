name := "ponyfx"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings", "–Xexperimental")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scaldi" %% "scaldi" % "0.5.7"
)

jfxSettings

JFX.mainClass := Some("demo.DemoApplication")
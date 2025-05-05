name := "SimpleSparkApp"
version := "1.0"
scalaVersion := "2.12.15"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.3.0" % "provided"
)

// Set source directory to code/src
Compile / scalaSource := baseDirectory.value / "code" / "src" / "main" / "scala"
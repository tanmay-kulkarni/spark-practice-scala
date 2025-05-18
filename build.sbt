name := "SimpleSparkApp"
version := "1.0"
scalaVersion := "2.12.15"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.3.0" % "provided"
)

// Set source directory to code/src
Compile / scalaSource := baseDirectory.value / "code" / "src" / "main" / "scala"

// Use cached resolution for dependencies
updateOptions := updateOptions.value.withCachedResolution(true)

// Faster compilation options
scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Xmax-classfile-name", "100"
)

// Use faster jvm options
javaOptions ++= Seq(
  "-Xmx2G",
  "-XX:+UseG1GC"
)

# Spark Scala Docker Playground

A simple development environment for writing and testing Apache Spark applications using Scala in Docker containers.

## Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop/)
- [docker-compose](https://docs.docker.com/compose/install/)
- [just](https://github.com/casey/just#installation) - a handy command runner

## Project Structure

```
project/
├── code/                  # Source code directory
│   └── src/
│       └── main/
│           └── scala/     # Scala source files go here
├── data/                  # Data files for processing
├── output/                # Spark job outputs will appear here
├── build.sbt              # SBT build configuration
├── docker-compose.yml     # Docker configuration
├── justfile               # Command runner recipes
└── README.md              # This file
```

## Quick Start

1. Clone this repository
2. Place your Scala source files in `code/src/main/scala/`
3. Place your data files in `data/`
4. Use the provided just commands to run your Spark applications

## Available Commands

Run `just` without arguments to see all available commands:

```bash
just
```

### Basic Workflow

```bash
# Compile Scala code
just compile

# Start Spark cluster
just start

# Submit a specific Spark job
just submit code/src/main/scala/MyApp.scala

# Check the job output
just check-output

# Stop Spark cluster
just stop

# Clean up everything
just cleanup
```

### Combined Commands

```bash
# Full workflow: compile + start + submit + check output
just run-all code/src/main/scala/MyApp.scala

# Quick iteration: compile + submit + check output
just iterate code/src/main/scala/MyApp.scala
```

### Utility Commands

```bash
# List all available Scala jobs
just list-jobs

# List all output directories
just list-outputs
```

## Example: Processing Data

The repository includes a sample data file and Spark application:

1. Sample data in `data/dummy.csv`:
```
id,name
1,cherry
2,tomato
3,potato
```

2. Sample Spark application in `code/src/main/scala/FruitProcessor.scala`

3. Run the sample application:
```bash
just run-all code/src/main/scala/FruitProcessor.scala
```

4. Check the output:
```bash
just check-output
```

## Spark UI

When the Spark cluster is running, you can access the Spark UI at:
- http://localhost:8080

## Writing Your Own Spark Applications

1. Create a new Scala file in `code/src/main/scala/`
2. Implement your Spark logic
3. Use `just` commands to compile, submit and monitor your job

Example Scala file template:

```scala
import org.apache.spark.sql.SparkSession

object MySparkApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("My Spark Application")
      .getOrCreate()
    
    // Your Spark code here
    // Use /opt/spark-data/ for input files
    // Use /opt/spark-output/ for output files
    
    spark.stop()
  }
}
```

## Adding Dependencies

To add external dependencies, modify the `build.sbt` file:

```scala
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.3.0" % "provided",
  "org.your.dependency" %% "dependency-name" % "x.y.z"
)
```

After modifying the build file, run `just compile` to download and include the new dependencies.

## Troubleshooting

- **Job fails with ClassNotFoundException**: Make sure your class name matches the file name exactly
- **Cannot find file**: Input paths inside containers should start with `/opt/spark-data/`
- **Output doesn't appear**: Output paths should start with `/opt/spark-output/`
- **Spark cluster not starting**: Check Docker is running with `docker ps`

## Cleaning Up

When you're done working with the project:

```bash
# Stop the Spark cluster
just stop

# For a complete cleanup (including output files and build artifacts)
just cleanup
```

## License

This project is open source and available under the [MIT License](LICENSE).
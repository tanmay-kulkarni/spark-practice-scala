# File: justfile

# Define default class path 
default_source := "SimpleApp.scala"

# Default recipe to run when just is called without arguments
default:
    @just --list

# Compile the Scala code with SBT in a Docker container
compile:
    @echo "Compiling Scala code..."
    docker run --rm -v $(pwd):/project \
      -v sbt-cache:/root/.sbt \
      -v ivy-cache:/root/.ivy2 \
      -v coursier-cache:/root/.cache/coursier \
      -w /project \
      hseeberger/scala-sbt:11.0.13_1.6.2_2.12.15 sbt package
    @echo "Compilation complete. JAR file created in target/scala-2.12/"

# Start the Spark cluster using docker-compose
start:
    @echo "Starting Spark cluster..."
    docker compose up -d
    @echo "Spark cluster started. Web UI available at http://localhost:8080"

# Submit a Spark job to the running cluster, with an optional source path
submit source=default_source:
    #!/usr/bin/env bash
    echo "Submitting Spark job from source: {{source}}"
    # Extract class name from source file (removes path and extension)
    CLASS_NAME=$(basename {{source}} .scala)
    echo "Using class name: $CLASS_NAME"
    docker exec spark-master spark-submit \
        --class $CLASS_NAME \
        --master spark://spark:7077 \
        /opt/spark-apps/target/scala-2.12/simpleSparkApp_2.12-1.0.jar
    echo "Job submitted"

# Check the output of the Spark job
check-output:
    @echo "Checking output in the output directory..."
    ls -la output/result
    @echo "Output files listed above"

# Stop the Spark cluster
stop:
    @echo "Stopping Spark cluster..."
    docker compose down
    @echo "Spark cluster stopped"

# Clean up all generated files and containers
cleanup:
    @echo "Cleaning up everything..."
    docker compose down --volumes --remove-orphans
    rm -rf output
    rm -rf target
    rm -rf project/target
    @echo "Cleanup complete"

# Full workflow: compile, start, submit, check output
run-all source=default_source: compile start (submit source) check-output
    @echo "Complete workflow executed successfully"

# Quick iteration: recompile and submit without restarting cluster
iterate source=default_source: compile (submit source) check-output
    @echo "Job iteration complete"

# List all available Scala files that can be submitted
list-jobs:
    #!/usr/bin/env bash
    echo "Available Spark jobs:"
    find code -name "*.scala" | sort

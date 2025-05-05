import org.apache.spark.sql.{SparkSession, functions => F}

object ReadData {
    def main(args: Array[String]): Unit = {

    // This is the entry point method of our Spark application
    // - def main: Defines a method named "main"
    // - args: Array[String]: Accepts command-line arguments as an array of strings
    // - Unit: Return type, similar to "void" in other languages (returns nothing)
    // This method signature is required for any standalone Scala application

    // Initialize Spark session
    val spark = SparkSession.builder().appName("Data Reader").getOrCreate()

    // This line imports Spark's implicits, which provides:
    // 1. Implicit conversions from Scala objects to DataFrame columns
    // 2. Encoder instances for common Scala types (Int, String, etc.)
    // 3. Methods to convert RDDs, Seqs, and other collections to DataFrames
    // 4. Symbol-to-column conversions (e.g., $"columnName" syntax)
    // These implicits are essential for DataFrame operations and expressions

    import spark.implicits._

    // Read the CSV file
    val df = spark.read.option("header", "true").option("inferSchema", "true").csv("/opt/spark-data/web_sales.csv")

    println("======= Print Schema =======")
    df.printSchema()

    println("========= Sample Data =========")
    df.show(5)

    // Count the number of rows in the dataframe
    val rowCount = df.count()
    println(s"======= Row Count: $rowCount =======")

    // Create a dataframe with the row count
    val countDF = Seq(("web_sales.csv", rowCount)).toDF("filename", "row_count")

    // Write the row count to an output file
    countDF.write.mode("overwrite").option("header", "true").csv("/opt/spark-output/row_count")

    println("Row count written to /opt/spark-output/row_count")

    spark.stop()
  }
}
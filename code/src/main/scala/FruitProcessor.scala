import org.apache.spark.sql.{SparkSession, functions => F}

object FruitProcessor {
  def main(args: Array[String]): Unit = {
    // Initialize Spark session
    val spark = SparkSession.builder()
      .appName("Fruit Processor")
      .getOrCreate()
    
    import spark.implicits._
    
    println("======= Starting Fruit Processor =======")
    
    // Read the CSV file
    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("/opt/spark-data/dummy.csv")
    
    println("======= Input Data =======")
    df.show()
    
    // Perform a simple transformation - uppercase the names and add a category column
    val processedDf = df.withColumn("name", F.upper($"name"))
      .withColumn("category", 
        F.when($"name" === "CHERRY", "fruit")
          .when($"name" === "TOMATO", "vegetable/fruit")
          .when($"name" === "POTATO", "vegetable")
          .otherwise("unknown")
      )
      .withColumn("processed_at", F.current_timestamp())
    
    println("======= Processed Data =======")
    processedDf.show()
    
    // Write the results to the output directory
    processedDf.write
      .mode("overwrite")
      .option("header", "true")
      .csv("/opt/spark-output/processed_fruits")
    
    println("======= Processing Complete =======")
    println("Results written to /opt/spark-output/processed_fruits")
    
    spark.stop()
  }
}
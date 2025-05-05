import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Simple Application")
      .getOrCreate()
    
    // Create some sample data
    val data = Seq((1, "John"), (2, "Alice"), (3, "Bob"))
    val df = spark.createDataFrame(data).toDF("id", "name")
    
    // Show data
    df.show()
    
    // Save output
    df.write.mode("overwrite").csv("/opt/spark-output/result")
    
    spark.stop()
  }
}
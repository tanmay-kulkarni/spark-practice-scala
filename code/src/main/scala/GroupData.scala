import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{expr, count}

object GroupData {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Drop Rows").getOrCreate()
        import spark.implicits._
        val df = spark.read.json("/opt/spark-data/customer.json")
        // simple count
        df.groupBy("customer_id").agg(count("address_id").alias("address_count")).show()
        
        spark.stop()

    }
}
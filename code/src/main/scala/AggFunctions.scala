import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{expr, max, min, mean, avg, count}

object AggFunctions {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Add New Columns").getOrCreate()
        import spark.implicits._
        val df = spark.read.option("header", "true").csv("/opt/spark-data/web_sales.csv")
        
        // you can use max and min directly on the dataframe w/o grouping
        df.select(max("ws_sales_price"), min("ws_sales_price")).show()
        
        // use can also use agg
        df.agg(max("ws_sales_price"), min("ws_sales_price"), avg("ws_sales_price"), count("ws_sales_price")).show()
        
        spark.stop()
                
    }
}
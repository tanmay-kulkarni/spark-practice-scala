import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{expr}

object ColumnArithmetic {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Add New Columns").getOrCreate()
        import spark.implicits._
        val df = spark.read.option("header", "true").csv("/opt/spark-data/web_sales.csv")

        var salesPerfDf = df.withColumn("expected_net_paid", $"ws_quantity" * $"ws_wholesale_cost")
        println("*** Adding column expected_net_paid ***")
        salesPerfDf.select("ws_sold_date_sk", "ws_ship_date_sk", "ws_quantity", "ws_wholesale_cost", "expected_net_paid").show(5)
        println("*** Adding column unit_cost ***")
        salesPerfDf = df.withColumn("unit_cost", expr("ws_wholesale_cost / ws_quantity"))
        salesPerfDf.select("ws_sold_date_sk", "ws_ship_date_sk", "ws_quantity", "ws_wholesale_cost", "unit_cost").show(5)
        
        spark.stop()
                
    }
}
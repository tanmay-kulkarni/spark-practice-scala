import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.expr

object RightJoin {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Drop Rows").getOrCreate()
        import spark.implicits._
        val df_customer = spark.read.json("/opt/spark-data/customer.json")
        val df_webSales = spark.read.option("header", "true").csv("/opt/spark-data/web_sales.csv")
        
        println("===Loaded both dataframes===")
        
        // customers with no sales
        df_webSales.join(df_customer, $"customer_id" === $"ws_bill_customer_sk", "right")
        .select("customer_id", "ws_bill_customer_sk")
        .where("ws_bill_customer_sk is null")
        .show(5)
        
        spark.stop()

    }
}
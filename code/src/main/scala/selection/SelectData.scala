import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.functions.{col,expr}

object SelectData {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().appName("CustomerReaderDDL").getOrCreate()
        import spark.implicits._
        
        var df = spark.read.format("csv").option("inferSchema", true).option("header", true).load("/opt/spark-data/web_sales.csv")
        println("====== Print Schema ======")
        df.printSchema()
        
        println(" === select method 1 ===")
        df.select("ws_sold_date_sk", "ws_ship_date_sk", "ws_bill_customer_sk").show(5)
        
        println("=== $ (dollar) syntax ===")
        df.select($"ws_ship_date_sk").show(5)
        
        println("=== ' (single quote) syntax")
        df.select('ws_ship_date_sk).show(5)
        
        println("=== col function syntax ===")
        df.select(col("ws_sold_date_sk"), col("ws_bill_customer_sk")).show(5)
        
        println("=== expr syntax ===")
        df.select(expr("concat(ws_sold_date_sk, ws_bill_customer_sk) as date_and_bill")).show(5)
        
        println("=== selectExpr syntax ===")
        df.selectExpr("ws_ship_date_sk as SHIP_DATE", "ws_bill_customer_sk as CUSTOMER_SK").show(5)

        spark.stop()
        }
}

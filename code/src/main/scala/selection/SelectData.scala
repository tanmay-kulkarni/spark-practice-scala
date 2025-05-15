import org.apache.spark.sql.{SparkSession, functions => F}

object SelectData {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().appName("CustomerReaderDDL").getOrCreate()

        var df = spark.read.format("csv").option("inferSchema", true).option("header", true).load("/opt/spark-data/web_sales.csv")
        println("====== Print Schema ======")
        df.printSchema()
        
        println(" === select method 1 ===")
        df.select("ws_sold_date_sk", "ws_ship_date_sk", "ws_bill_customer_sk").show(5)
        
        spark.stop()
        
        }
}

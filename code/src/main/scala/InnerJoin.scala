import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.expr

object InnerJoin {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Drop Rows").getOrCreate()
        import spark.implicits._
        val df_customer = spark.read.json("/opt/spark-data/customer.json")
        
        val df_address = spark.read.parquet("/opt/spark-data/address.parquet")
        
        println("===Loaded both dataframes===")
        
        val df_customerWithAddress = df_customer.join(df_address, df_customer.col("address_id") === df_address.col("address_id"),"inner")
                
        df_customerWithAddress.select("customer_id", "firstname", "lastname", "location_type", "country", "city", "street_name").show(5)
                
        spark.stop()

    }
}
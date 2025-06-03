import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.lit

object UnionData {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Drop Rows").getOrCreate()
        import spark.implicits._
        val df_customer = spark.read.json("/opt/spark-data/customer.json")
        
        val df1 = df_customer.select("firstname", "lastname", "customer_id").withColumn("from", lit("df1"))
        val df2 = df_customer.select("firstname", "lastname", "customer_id").withColumn("from", lit("df2"))
        
        df1.union(df2).sample(false, 0.5).show(200)
        
        spark.stop()

    }
}
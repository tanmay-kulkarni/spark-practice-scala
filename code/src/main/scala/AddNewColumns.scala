import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType}
import org.apache.spark.sql.functions.{col, concat, concat_ws, lit}

object AddNewColumns {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().appName("Add New Columns").getOrCreate()
        import spark.implicits._
        val df = spark.read.json("/opt/spark-data/customer.json")
        println("===No. of columns:===")
        println(df.columns.size)
        
        println("===WithColumn===")
        
        val df2 = df.withColumn("full_name", concat(col("firstname"), col("lastname")))
        df2.select("firstname", "lastname", "full_name").show(5)
        
        val df3 = df.withColumn("fullname", concat_ws(", ", $"firstname", $"lastname"))
        df3.select("firstname", "lastname", "fullname").show(5)
        
        df3.withColumn("code", lit("007")).select("firstname", "lastname", "fullname", "code").show(5)
        
        spark.stop()
    }
}
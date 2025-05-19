import org.apache.spark.sql.SparkSession


object RemoveColumns {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().appName("Add New Columns").getOrCreate()
        import spark.implicits._
        val df = spark.read.json("/opt/spark-data/customer.json")
        println("===No. of columns:===")
        println(df.columns.size)
        println("=== Columns: ===")
        println(df.columns.mkString(", "))
        println("~~~ print columns ended ~~~")
        // println("=== Dropping columns ===")
        var newColumnsStr = df.drop("address_id", "birth_country", "salutation", "tfhahjlksd").columns 
        println("=== List of columns after dropping ===")
        println(newColumnsStr.mkString(", "))
        println("*** Note that specifying an invalid column name 'tfhahjlksd' in the drop method DID NOT cause it to fail ***")
        
        spark.stop()
                
    }
}
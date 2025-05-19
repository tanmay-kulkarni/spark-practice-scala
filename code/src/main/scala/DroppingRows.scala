import org.apache.spark.sql.SparkSession

object DroppingRows {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Drop Rows").getOrCreate()
        import spark.implicits._
        
        val df = List(
            (1, "Monday"),
            (1, "Monday"),
            (2, "Tuesday"),
            (3, "Tuesday"),
            (3, "Wednesday"),
            (4, "Thursday"),
            (5, "Friday"),
            (6, "Saturday"),
            (7, "Sunday"),
            (8, "Monday")
            ).toDF("id", "name")
        
        df.show()
        
        // Drop duplicate rows using distinct
        df.distinct.show()
        
        // Use dropDuplicates to remove dups based on secific columns
        df.dropDuplicates("name").show()
        
        // this is same as df.distinct()
        df.dropDuplicates().show()
        
        // based on > 1 columns
        println("Dropping based on > 1 columns")
        df.dropDuplicates("id", "name").show()
        
        spark.stop()

    }
}
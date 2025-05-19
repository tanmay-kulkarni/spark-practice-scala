import org.apache.spark.sql.SparkSession

object HandlingNulls {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Drop Rows").getOrCreate()
        import spark.implicits._
        val df = spark.read.json("/opt/spark-data/customer.json")
        
        val df2 = df.selectExpr("salutation", "firstname", "lastname", "email_address", "year(birthdate) as birthyear")
        df2.show()
        
        println("===Counting the no. of records in df2===")
        println("=== Total rows in df2: " + df2.count)
        
        // filter out nulls
        df2.where($"salutation".isNotNull).show(5)
        
        // keep only null values
        df2.where($"salutation".isNull).show(5)
        
        // DO NOT use the following syntax, it returns incorrect results
        // df2.where($"salutation" === null)
        
        // use .na syntax
        println("=== Dropping records where all columns are null ===")
        df2.na.drop(how="all").show(5)
        println("=== Remove records where any columns are null ===")
        df2.na.drop("any").show(5)
        println("=== Remove records where some of the columns are null ===")
        df2.na.drop("any", Seq("firstname", "lastname")).show(5)

        // Filling null values
        df2.na.fill("UNAVAILABLE").show()
        
        // Map to fill in multiple values        
        df2.na.fill(Map(
                            "salutation" -> "Unknown", 
                            "firstname" -> "John", 
                            "lastname" -> "Snow",
                            "email_address" -> "abc@unknown.com",
                            "birthyear" -> 9999
                    )
        )
        .show(20)

        
        spark.stop()

    }
}
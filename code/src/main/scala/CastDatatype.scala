import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType}

object CastDatatype {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Change Data Type").getOrCreate()
        import spark.implicits._
        
        var df = spark.read.format("json").load("/opt/spark-data/customer.json")
        
        println("=== Sample data ===")
        df.show(5)
        
        println("=== Existing Types ===")
        df.printSchema()
        
        df.select($"address_id".cast("long"), $"birthdate".cast(StringType)).show()
        
        println("=== Using selectExpr ===")
        df.selectExpr("cast(address_id as string)", "cast(demographics.income_range[0] as double) as lowerBound").show(5)
    
        spark.stop()
        
        }
}
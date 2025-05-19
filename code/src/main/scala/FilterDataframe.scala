import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{expr, col, year, month}

object FilterDataframe {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Add New Columns").getOrCreate()
        import spark.implicits._
        val df = spark.read.json("/opt/spark-data/customer.json")
        
        println("=== Filtering dataframe on conditions ===")
        // .filter and .where can be used interchangeably
        df
        .where(year($"birthdate") > 1980)
        .filter(month('birthdate) === 1)
        .where("day(birthdate) > 15")
        .filter($"birth_country" =!= "UNITED STATES")
        .select("firstname", "lastname", "birthdate", "birth_country")
        .show(5)
        
        spark.stop()
                
    }
}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.expr

object SortData {
    def main(args: Array[String]): Unit = {
        
        val spark = SparkSession.builder().appName("Drop Rows").getOrCreate()
        import spark.implicits._
        val df = spark.read.json("/opt/spark-data/customer.json")
        
        df.na.drop("any")
        .sort("firstname")
        .select("firstname", "lastname", "birthdate")
        .show(10)
        
        // can also use orderBy instead of sort
        df.na.drop("any")
        .orderBy(expr("month(birthdate)").desc) // notice where desc is specified
        .select("firstname", "lastname", "birthdate")
        .show(10)
        
        // sort by multiple columns
        df.na.drop("any")
        .orderBy($"firstname", $"lastname".desc)
        .select("firstname", "lastname", "birthdate")
        .show(10)
        
        spark.stop()

    }
}
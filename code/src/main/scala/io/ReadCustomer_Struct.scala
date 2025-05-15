import org.apache.spark.sql.{SparkSession, functions => F}
import org.apache.spark.sql.types.{LongType, StringType, StructType, StructField, ArrayType}

object ReadCustomer_Struct {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().appName("ReadData_DDL").getOrCreate()

        var df1 = spark.read.json("/opt/spark-data/customer.json")

        println("==== Read Dataframe customer.json ====")
        println("==== Now printing schema ====")
        df1.printSchema()

        val schema = df1.schema

        println("schema:" + schema.toString)

        val schema2 = StructType(Array(
            StructField("address_id", LongType, true),
            StructField("birth_country", StringType, true),
            StructField("birthdate", StringType, true),
            StructField("customer_id", LongType, true),
            StructField("demographics", StructType(Array(
                StructField("buy_potential", StringType, true),
                StructField("credit_rating", StringType, true),
                StructField("education_status", StringType, true),
                StructField("income_range", ArrayType(LongType, true), true),
                StructField("purchase_estimate", LongType, true),
                StructField("vehicle_count", LongType, true)
            )), true),
            StructField("email_address", StringType, true),
            StructField("firstname", StringType, true),
            StructField("gender", StringType, true),
            StructField("is_preferred_customer", StringType, true),
            StructField("lastname", StringType, true),
            StructField("salutation", StringType, true)
        ))

        val df2 = spark.read.format("json").schema(schema2).load("/opt/spark-data/customer.json")
        println("Schema of df2:")
        df2.printSchema()

        spark.stop()
        
        }
}

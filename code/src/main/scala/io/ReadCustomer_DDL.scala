import org.apache.spark.sql.{SparkSession, functions => F}

object ReadCustomer_DDL {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().appName("CustomerReaderDDL").getOrCreate()

        // import spark.implicits._

        // read json
        var df = spark.read.format("json").load("/opt/spark-data/customer.json")
        println("====== Print Schema ======")
        df.printSchema()
        df.show(5)

        // specify the schema using "Data Definition Language Formatted String"
        val customerDFSchema_DDL = """
                                    address_id INT, 
                                    birth_country STRING, 
                                    birthdate date, 
                                    customer_id INT, 
                                    demographics STRUCT<buy_potential: STRING, 
                                                        credit_rating: STRING, 
                                                        education_status: STRING, 
                                                        income_range: ARRAY<INT>, 
                                                        purchase_estimate: INT, 
                                                        vehicle_count: INT>, 
                                    email_address STRING, 
                                    firstname STRING, 
                                    gender STRING, 
                                    is_preferred_customer BOOLEAN, 
                                    lastname STRING, 
                                    salutation STRING
                                    """
        
        df = spark.read.format("json").schema(customerDFSchema_DDL).load("/opt/spark-data/customer.json")
        df.printSchema()
        
        spark.stop()
        }
}

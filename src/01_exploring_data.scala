// Databricks notebook source
// MAGIC %fs ls /FileStore/tables

// COMMAND ----------

val customerDF = spark.read.format("json").option("inferSchema", true).load("/FileStore/tables/dcad_data/customer.json")

// COMMAND ----------

customerDF.printSchema

// COMMAND ----------

display(customerDF)

// COMMAND ----------

val tempDF = customerDF.select("address_id", "birth_country", "customer_id")

// COMMAND ----------

// MAGIC %md
// MAGIC Using col, column

// COMMAND ----------

import org.apache.spark.sql.functions.{col, column}

val tempDF = customerDF.select(col("address_id"), column("birth_country"))

display(tempDF)

// COMMAND ----------

display(tempDF)

// COMMAND ----------

// MAGIC %md
// MAGIC Using expr

// COMMAND ----------

import org.apache.spark.sql.functions.expr

val tempDF = customerDF.select(expr("address_id as address___id"), expr("cast(customer_id as STRING) as customer_id_str"))

display(tempDF)


// COMMAND ----------

// MAGIC %md
// MAGIC using single quotes

// COMMAND ----------

val tempDF = customerDF.select($"demographics.buy_potential", $"demographics.credit_rating", 'customer_id, 'firstname, 'gender)

display(tempDF)
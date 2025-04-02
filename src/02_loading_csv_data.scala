// Databricks notebook source
// MAGIC %fs ls /FileStore/tables/dcad_data

// COMMAND ----------

val webSalesDF = spark.read.options(Map("inferSchema" -> "true", "header" -> "true")).csv("/FileStore/tables/dcad_data/web_sales.csv")

// COMMAND ----------

display(webSalesDF)

// COMMAND ----------

webSalesDF.count()

// COMMAND ----------

webSalesDF.printSchema()
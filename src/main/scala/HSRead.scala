/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.common.primitives.UnsignedBytes
//import org.apache.hadoop.examples.terasort.{TeraInputFormat, TeraOutputFormat}

import org.apache.hadoop.io.Text
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by cnarasim on 1/18/17.
  */
object HSRead {

  implicit val caseInsensitiveOrdering = UnsignedBytes.lexicographicalComparator

  def main(args: Array[String]) {

    if (args.length < 1) {
      println("Usage:")
      println("DRIVER_MEMORY=[mem] spark-submit " +
        "HSSort " +
        "TPCx-HS-master_Spark.jar " +
        "[input-sort-directory] [output-sort-directory]")
      println(" ")
      println("Example:")
      println("DRIVER_MEMORY=50g spark-submit " +
        "HSSort " +
        "TPCx-HS-master_Spark.jar " +
        " hdfs://username/HSsort_input hdfs://username/HSsort_output")
      System.exit(0)
    }
    val conf = new SparkConf().setAppName("HSRead").
      registerKryoClasses(Array(classOf[Text])).setAppName("HSRead")
      .setMaster("local") //新加的

    val sc = new SparkContext(conf)
    sc.setLogLevel("INFO")

    try {

      val inputFile = "file:///"+args(0)

      // Read and Sort and Write to a new file
      // val partition = args(1).toInt

      val data = sc.newAPIHadoopFile(inputFile,
        classOf[HadoopHSInputFormat],
        classOf[Text],
        classOf[Text])


      data.count()


    }catch{
      case e: Exception => println("Spark HSSort Exception" + e.getMessage() + e.printStackTrace())
    }finally {
      sc.stop()
    }
  }


}

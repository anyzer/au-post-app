package bran.post.helper

import java.io.{BufferedWriter, File, FileWriter}

import bran.Order_File
import bran.post.base.Shipments_Details
import bran.post.utilities.{CreateOrderFromShipment, GetOrderSummary}

import scala.io.Source
import scala.util.Try

case class Shipment_Details(shipment_id: String, column_B: String, column_C: String)

object ExcelHelper {

  def getShipmentsDetails(postPara: Input_Para, getOrderSummary: (Input_Para, Shipment_Details) => Try[Unit]): Try[Unit] = {
    val source = s"./${postPara.readFolder.path}/Shipments_${Helper.todayDate("yyyyMMdd")}.csv"
    println(s"Read from ${source}")

    Try {
      val bufferedSource = Source.fromFile(source)

      for (line <- bufferedSource.getLines().drop(1)) {
        val array: Array[String] = line.split(",").map(_.trim)
        val shipment_details: Shipment_Details = Shipment_Details(array(0), array(1), array(2))

        println(getOrderSummary(postPara, shipment_details))
      }

      bufferedSource.close()
    }
  }


  // check if we can find all shipments from consignment
  def checkShipmentsForAllConsignments(configPara: Input_Para, listOfShipments: List[Shipments_Details], getOrderSummary: (String, List[Shipments_Details]) => Map[String, Option[String]]): Try[Unit] = {
    val source = s"./${configPara.readFolder.path}/Consignment_${Helper.todayDate("yyyyMMdd")}.csv"
    val destination = s"./${configPara.readFolder.path}/Consignment_${Helper.todayDate("yyyyMMdd")}_Shipment.csv"
    writeCsv(destination, s"CONSIGNMENT ,SHIPMENTS               ,Datetime       ,SHIPMENTS STATUS \n", false)

    Try {
      val bufferedSource = Source.fromFile(source)
      val allLines = bufferedSource.getLines()
      println(s"\nTheere are ${allLines.size} consignments in ${source}\n")

      for (line <- Source.fromFile(source).getLines()) {
        val consignmentToShipment: Map[String, Option[String]] = getOrderSummary(line, listOfShipments)
        val shipment_id: Option[String] = consignmentToShipment.get(line).flatten

        if (shipment_id == None) {
          println(s"ERROR => Consignment <${line}> cannot find corresponding Shipment")
          writeCsv(destination, s"${line},ERROR-CannotFindShipment,${Helper.todayDate},N/A\n")
        } else if(shipment_id.get.contains("Consignment_")) {
          println(s"ERROR => <${shipment_id.get}> is invalid")
          writeCsv(destination, s"${line},ERROR-${shipment_id.get},${Helper.todayDate},${shipment_id.get.split("_")(1)}\n")
        }else {
          writeCsv(destination, s"${line},${shipment_id.get},${Helper.todayDate},Created\n")
        }
      }
      bufferedSource.close()
    }

  }

  //process those shipments with consignment to create orders
  def getOrderSummary(configPara: Input_Para): Try[Order_File] = {
    val source = s"./${configPara.readFolder.path}/Consignment_${Helper.todayDate("yyyyMMdd")}_Shipment.csv"
    val destination = s"./${configPara.readFolder.path}/Order_Summary_${Helper.todayDate("yyyyMMdd")}.csv"
    writeCsv("Order Summary List", destination, false)

    val res: Try[Order_File] = Try {
      val bufferedSource = Source.fromFile(source)
      val lines: List[String] = bufferedSource.getLines().drop(1).toList

      val shipments_list: List[String] = lines.map{ x => x.split(",")(1) }
      val order_file: Try[Order_File] = CreateOrderFromShipment.createOrdersFromShipments(shipments_list, configPara)
        .map(_.order.order_id)
        .map { o =>
          val fileName: String = configPara.postConfig.account + "_" + o + "_" + Helper.todayDate + ".pdf"
          GetOrderSummary.getOrderSummary(o, configPara, fileName)
//          writeCsv(o, destination)

          Order_File(o, fileName)
        }
      bufferedSource.close()
      order_file
    }.flatten

    if (res.isFailure) {
      println(s"Error to update order summary, please check file ${destination} and folder ./order_summary")
      System.exit(1)
    }

    res
  }


  //Check Consignment_date_Shipment.csv file to check if it contains <Cannot find ShipmentId>
  def checkConsignmentsAndShipments(file: String): Try[Int] = {
    Try {
      val bufferedSource = Source.fromFile(file)
      val errors: Int = bufferedSource.getLines()
        .filter(x => x.contains("ERROR"))
        .size
      bufferedSource.close()
      errors
    }
  }


  private def writeCsv(filePath: String, content: String, append: Boolean = true): Try[Unit] = {
    println(content)
    Try {
      val file = new File(filePath)
      val bw = new BufferedWriter(new FileWriter(file, append))
      bw.write(content)
      bw.close()
    }
  }

}

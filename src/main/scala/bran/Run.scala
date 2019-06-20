package bran

import bran.post.base.Shipments_Details
import bran.post.helper._
import bran.run.Run_Helper
import scala.io.StdIn.{readLine}
import scala.util.{Failure, Success, Try}

case class Order_File(order_id: String, order_summary_file: String)

object Run {
  def main(args: Array[String]): Unit = {
    val configPara: Option[Input_Para] = Helper.getConfig(args)
    println(s"Configuration: ${configPara}")

    configPara.get.env match {
      case Env("test") => Helper.create_shipments_labels(configPara.get).map { x =>
        println("Success - created order summary - test")
        MailerService
          .sendMessage("Do not reply - Order " + x.order_id + " - test",
            "\nFYI - Shipments Orders Summary - test\n",
            x.order_summary_file)
      }.flatten match {
        case Success(s) => println("Success - send as email attachment - test")
        case Failure(f) => println(f.getMessage)
      }

      case Env("teest") => {
        // get shipment lists, in shipments JSON can find
        val shipmentsDetails: Try[List[Shipments_Details]] = Run_Helper.getShipmentList(configPara)
        println(s"================= ${shipmentsDetails.get.size} =========================\n")

        // map[Consignment, Shipment, Datetime], and write to Consignment_yyyyMMdd_Shipment.csv
        val destination: String = Run_Helper.checkShipmentsForEveryConsignment(configPara, shipmentsDetails)

        val name = readLine("Do you want to proceed (yes/no) -> ")
        if(name.toUpperCase().equals("NO")) System.exit(0)

        //create order from shipment and get order summary
        val order_summaries: Try[Unit] = ExcelHelper.getOrderSummary(configPara.get).map { x =>
          println("Order " + x.order_id + " is created successfully")
          MailerService
            .sendMessage("Do not reply - Order " + x.order_id + " - test",
              "\nFYI - Shipments Orders Summary - teest\n",
              x.order_summary_file)
        }

        if (order_summaries.isSuccess) println("All Success")

      }

      case Env("prod") => ExcelHelper.getShipmentsDetails(configPara.get, getOrderSummary _) match {
        case Success(s) => println("Success - send as email attachment")
        case Failure(f) => println(f.getMessage)
      }

      case _ => println("Environment can only be either <test> or <prod>")
    }

  }


  def getOrderSummary(configPara: Input_Para, shipment_details: Shipment_Details): Try[Unit] = {
    Success()
  }


}
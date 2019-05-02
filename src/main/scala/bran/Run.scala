package bran

import bran.post.helper._

import scala.util.{Failure, Success, Try}

case class Order_File(order_id: String, order_summary_file: String)

object Run {
  def main(args: Array[String]): Unit = {
    val configPara: Option[Input_Para] = Helper.getConfig(args)
    println(s"Configuration: ${configPara}")

    configPara.get.env match {
      case Env("test") => Helper.get_order_summary(configPara.get).map { x =>
        println("Success - created order summary - test")
        MailerService
          .sendMessage("Do not reply - Order " + x.order_id + " - test",
            "\nFYI - Shipments Orders Summary - test\n",
            x.order_summary_file)
      }.flatten match {
        case Success(s) => println("Success - send as email attachment - test")
        case Failure(f) => println(f.getMessage)
      }
      case Env("prod") => ExcelHelper.getShipmentsDetails(configPara.get, getOrderSummary) match {
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
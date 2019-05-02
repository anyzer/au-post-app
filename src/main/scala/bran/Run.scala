package bran

import bran.post.base.request.response.Account
import bran.post.base.response
import bran.post.helper.{Helper, Input_Para, MailerService}
import bran.post.utilities._

import scala.util.{Failure, Success, Try}

case class Order_File(order_id: String, order_summary_file: String)

object Run {
  def main(args: Array[String]): Unit = {
    val configPara: Option[Input_Para] = Helper.getConfig(args)
    println(s"Configuration: ${configPara}")

    get_order_summary(configPara.get)
      .map { x =>
        println("Success - created order summary")
        MailerService
          .sendMessage("Do not reply - Order " + x.order_id,
            "\nFYI - Shipments Orders Summary\n",
            x.order_summary_file)
      }.flatten match {
      case Success(s) => println("Success - send as email attachment")
      case Failure(f) => println(f.getMessage)
    }
  }


  def get_order_summary(configPara: Input_Para): Try[Order_File] = {
    //=== get account ===
    val account: Try[Account] = GetAccount.getAccount(configPara)

    //=== create shipments and return shipment_id ===
    val shipment_id: Try[String] = account
      .map(CreateShipment
        .createShipment(_, configPara))
      .flatten
      .map(_.shipments
        .head
        .shipment_id)

    //=== create print label ===
    val label: Try[response.PrintLabels] = shipment_id
      .map(CreateLabel
        .createLabel(_, configPara)).flatten
    Thread.sleep(9900)

    //=== create order from shipment ===
    val orderFromShipment: Try[response.Order] = shipment_id
      .map(CreateOrderFromShipment
        .createOrderFromShipment(_, configPara)).flatten

    val order_id: Try[String] = orderFromShipment
      .map(_.order.order_id)

    //=== get order summary ===
    order_id.map { x =>
      val fileName: String = configPara.postConfig.account + "_" + x + "_" + Helper.todayDate + ".pdf"
      GetOrderSummary
        .getOrderSummary(x, configPara, fileName)
      Order_File(x, fileName)
    }
  }
}
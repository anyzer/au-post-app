package bran

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}
import bran.post.base.request.response.Account
import bran.post.base.response
import bran.post.helper.{Helper, Input_Para}
import bran.post.utilities._

object Run {
  def main(args: Array[String]): Unit = {
    val configPara: Option[Input_Para] = Helper.getConfig(args)
    println("Configuration: " + configPara)

    //=== get account ===
    val acc: String = GetAccount.getAccount(configPara.get).get
    println("Get Account: " + acc + "\n")
    val account: Account = GetAccount.account(acc)

    //=== create shipments ===
    val shipment: String = CreateShipment.createShipment(account, configPara.get).get
    println("Shipment Response: " + shipment)
    val shipments: response.Shipments = CreateShipment.shipments(shipment)

    val shipment_id: String = shipments.shipments.head.shipment_id

    //=== create print label ===
    val labelString: String = CreateLabel.createLabel(shipment_id, configPara.get).get
    println("Print Label Response: " + labelString)
    val label: response.PrintLabels = CreateLabel.label(labelString)

    //it need few seconds to process print label
    Thread.sleep(6000)

    //=== create order from shipment ===
    val orderFromShipment: String = CreateOrderFromShipment.createOrderFromShipment(shipment_id, configPara.get).get
    println("Create Order from Shipment Response: " + orderFromShipment)
    val orderFromShip: response.Order = CreateOrderFromShipment.orderFromShipment(orderFromShipment)

    val order_id: String = orderFromShip.order.order_id
    println(s"\norderFromShip.order.order_id: ${orderFromShip.order.order_id}\n")

    //=== get order summary ===
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    val todayDate = LocalDateTime.now(ZoneId.of("Australia/Sydney")).format(formatter)
    val fileName = configPara.get.postConfig.account + "_" + order_id + "_" + todayDate + ".pdf"
    println("base fileName: " + fileName)
    val getOrderSummary = GetOrderSummary.getOrderSummary(order_id, configPara.get, fileName)
    println("Get Order Summary file: " + getOrderSummary)
  }
}
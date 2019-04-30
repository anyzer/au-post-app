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
    println("Get Account Res: \n" + acc)
    val account: Account = GetAccount.account(acc)

    //=== create shipments ===
    val shipment: String = CreateShipment.createShipment(account, configPara.get).get
    println("Create Shipments Res: \n" + shipment)
    val shipments: response.Shipments = CreateShipment.shipments(shipment)
    val shipment_id: String = shipments.shipments.head.shipment_id

    //=== create print label ===
    val labelString: String = CreateLabel.createLabel(shipment_id, configPara.get).get
    println("Create Label Res: \n" + labelString)
    val label: response.PrintLabels = CreateLabel.label(labelString)
    Thread.sleep(9000)

    //=== create order from shipment ===
    val orderFromShipment: String = CreateOrderFromShipment.createOrderFromShipment(shipment_id, configPara.get).get
    println("Order From Shipment Res: \n" + orderFromShipment)
    val orderFromShip: response.Order = CreateOrderFromShipment.orderFromShipment(orderFromShipment)
    val order_id: String = orderFromShip.order.order_id

    //=== get order summary ===
    val todayDate = LocalDateTime.now(ZoneId.of("Australia/Sydney"))
      .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
    val fileName = configPara.get.postConfig.account + "_" + order_id + "_" + todayDate + ".pdf"
    val getOrderSummary = GetOrderSummary.getOrderSummary(order_id, configPara.get, fileName)
    println("Get Order Summary file: " + getOrderSummary)
  }
}
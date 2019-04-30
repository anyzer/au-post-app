package bran

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}

import bran.post.base.request.response.Account
import bran.post.base.response
import bran.post.config.PostConfig
import bran.post.utilities._

case class Env(env: String)

case class Folder(path: String)

case class Input_Para(env: Env, readFolder: Folder, writeFolder: Folder, postConfig: PostConfig)

case class Account_Details(account_number: String, name: String, address_lines: List[String],
                           address_suburb: Option[String], address_state: String, address_country: String)

object Run {

  def main(args: Array[String]): Unit = {
    val configPara: Option[Input_Para] = getConfig(args)
    println("Configuration: " + configPara)

//    //=== get account ===
//    val acc: String = GetAccount.getAccount(configPara.get).get
//    println("Get Account: " + acc + "\n")
//    val account: Account = GetAccount.account(acc)
//
//    //=== create shipments ===
//    val shipment: String = CreateShipment.createShipment(account, configPara.get).get
//    println("Shipment Response: " + shipment)
//    val shipments: response.Shipments = CreateShipment.shipments(shipment)
//
//    val shipment_id: String = shipments.shipments.head.shipment_id
//
//    //=== create print label ===
//    val labelString: String = CreateLabel.createLabel(shipment_id, configPara.get).get
//    println("Print Label Response: " + labelString)
//    val label: response.PrintLabels = CreateLabel.label(labelString)
//
//    //it need few seconds to process print label
//    Thread.sleep(6000)
//
//    //=== create order from shipment ===
//    val orderFromShipment: String = CreateOrderFromShipment.createOrderFromShipment(shipment_id, configPara.get).get
//    println("Create Order from Shipment Response: " + orderFromShipment)
//    val orderFromShip: response.Order = CreateOrderFromShipment.orderFromShipment(orderFromShipment)
//
//    val order_id: String = orderFromShip.order.order_id
//    println(s"\norderFromShip.order.order_id: ${orderFromShip.order.order_id}\n")
//
//    //=== get order summary ===
//    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
//    val todayDate = LocalDateTime.now(ZoneId.of("Australia/Sydney")).format(formatter)
//    val fileName = shipment_id + "_" + order_id + "_" + todayDate + ".pdf"
//    val getOrderSummary = GetOrderSummary.getOrderSummary(order_id, configPara.get, fileName)
//    println("Get Order Summary file: " + getOrderSummary)

  }


  def getConfig(args: Array[String]): Option[Input_Para] = {
    if (args.length == 3 && (args(0) == "test" || args(0) == "prod"))
      Option(Input_Para(Env(args(0)), Folder(args(1)), Folder(args(2)), PostConfig.get(Env(args(0)))))
    else if (args.length == 1 && (args(0) == "test" || args(0) == "prod")) {
      val config: PostConfig = PostConfig.get(Env(args(0)))
      Option(Input_Para(Env(args(0)), Folder(config.read_path), Folder(config.write_path), config))
    }
    else {
      println("Sample command: <prod read/folder/ write/folder/> <test read/folder/ write/folder/> \n" +
        "prod|test or " + "zero command line parameters default to test environment" +
        "there should be 3 params, and first parameter should be either 'test' or 'prod'")
      None
    }
  }

}
package bran.post.helper

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}

import bran.Order_File
import bran.post.base.request.response.Account
import bran.post.base.response.{Response_Shipment, Shipments}
import bran.post.base.{Items_Details, Shipments_Details, response}
import bran.post.config.PostConfig
import bran.post.utilities._
import com.google.gson.{JsonObject, JsonParser}

import scala.util.{Failure, Try}

case class Env(env: String)

case class Folder(path: String)

case class Input_Para(env: Env, readFolder: Folder, writeFolder: Folder, postConfig: PostConfig)

case class Account_Details(account_number: String, name: String, address_lines: List[String],
                           address_suburb: Option[String], address_state: String, address_country: String)

object Helper {

  def getConfig(args: Array[String]): Option[Input_Para] = {
    if (args.length == 3 && (args(0) == "test" || args(0) == "prod" || args(0) == "teest"))
      Option(Input_Para(Env(args(0)), Folder(args(1)), Folder(args(2)), PostConfig.get(Env(args(0)))))
    else if (args.length == 1 && (args(0) == "test" || args(0) == "prod" || args(0) == "teest")) {
      val config: PostConfig = PostConfig.get(Env(args(0)))
      Option(Input_Para(Env(args(0)), Folder(config.read_path), Folder(config.write_path), config))
    }
    else {
      println("Sample command: <prod read/folder/ write/folder/> <test read/folder/ write/folder/> \n\n" +
        "There should be either 1 or 3 params, and first parameter should be either 'test' or 'prod'\n\n"
      )
      None
    }
  }


  def todayDate: String = LocalDateTime.now(ZoneId.of("Australia/Sydney"))
    .format(DateTimeFormatter
      .ofPattern("yyyyMMdd_HHmmss"))

  def todayDate(format: String): String = {
    LocalDateTime.now(ZoneId.of("Australia/Sydney"))
      .format(DateTimeFormatter
        .ofPattern(format))
  }


  def get_shipments_list(configPara: Input_Para): Try[List[Response_Shipment]] = {
    val shipments: Try[Shipments] = GetShipmentList.getShipmentList(0, configPara, "fileName")
    shipments.map { x =>
      println("There are " + x.shipments.size + " shipments")
      println("In Pagination, there are " + x.pagination.map(y => y.total_number_of_records) + "shipments")
      x.shipments
    }
  }


  def getShipmentsDetails(list: List[Response_Shipment]): List[Shipments_Details] = {
    list.map { x =>
      val shipment_id: String = x.shipment_id
      val shipment_status: String = x.shipment_summary.status

      val items: List[Items_Details] = x.items.map { m =>
        val item_id = m.item_id
        val item_consignment_id = m.tracking_details.consignment_id
        Items_Details(item_id, item_consignment_id)
      }

      println(shipment_id + " => " + items)
      Shipments_Details(shipment_id, shipment_status, items)
    }
  }


  def getShipmentConsignmentMap(consignments_id: List[String], shipments_details: List[Shipments_Details]): Map[String, Option[String]] = {
    consignments_id.map { x => compareConsignment(x, shipments_details)}
      .flatten.toMap
  }


  // One Consignment has a matching Shipments, multiple matching or status is not Created are all invalid consignment
  def compareConsignment(consignment_id: String, shipments_details: List[Shipments_Details]): Map[String, Option[String]] = {
    val shipment: List[Shipments_Details] = shipments_details.filter { x => itemsWithConsignment(consignment_id, x.items_details)}
    if(shipment.size != 1) {
      println(s"For the consignment ${consignment_id}, there are < ${shipment.size} > matching shipment(s)")
      shipment.map(x => println(s"Shipment ID: ${x}"))
      Map(consignment_id -> None)
    }else {
      val head = shipment.head
      if(head.shipment_status.equals("Created")) {
        Map(consignment_id -> Some(shipment.head.shipment_id))
      } else {
        println(s"For the consignment ${consignment_id}, its status < ${head.shipment_status} > is INVALID")
        Map(consignment_id -> Some(s"Consignment_${head.shipment_status}"))
      }
    }
  }


  // For a certain shipment, all items should have the same consignment id
  def itemsWithConsignment(consignment_id: String, items_details: List[Items_Details]): Boolean = {
    items_details.filter {
      _.consignment_id == consignment_id
    }.size == items_details.size
  }


  def create_shipments_labels(configPara: Input_Para): Try[Order_File] = {
    //=== get account ===
    println(s"\nGet account - ${configPara.env}")
    val account: Try[Account] = GetAccount.getAccount(configPara)

    //=== create shipments and return shipment_id ===
    println(s"\nCreate shipment - ${configPara.env}")
    val shipment_id: Try[String] = account
      .map(CreateShipment
        .createShipment(_, configPara))
      .flatten
      .map(_.shipments
        .head
        .shipment_id)

    println("Shipment ID" + shipment_id)

    //=== create print label ===
    println(s"\nCreate Print label - ${configPara.env}")
    val label: Try[response.PrintLabels] = shipment_id
      .map(CreateLabel
        .createLabel(_, configPara)).flatten

    println("\nWait for label ...")

    Thread.sleep(9900)

    //=== create order from shipment ===
    println(s"\nCreate order from shipment - ${configPara.env}")
    val orderFromShipment: Try[response.Order] = shipment_id
      .map(CreateOrderFromShipment
        .createOrderFromShipment(_, configPara)).flatten

    val order_id: Try[String] = orderFromShipment
      .map(_.order.order_id)

    //=== get order summary ===
    println(s"\nGet order summary - ${configPara.env}")
    order_id.map { x =>
      val fileName: String = configPara.postConfig.account + "_" + x + "_" + Helper.todayDate + ".pdf"
      GetOrderSummary
        .getOrderSummary(x, configPara, fileName)
      Order_File(x, fileName)
    }
  }


  def create_order_summary_from_consignment(shipment_id: String, configPara: Input_Para): Try[Order_File] = {
    //=== create order from shipment ===
    println(s"\nCreate order from shipment - ${configPara.env}")
    val orderFromShipment: Try[response.Order] = CreateOrderFromShipment.createOrderFromShipment(shipment_id, configPara)
    val order_id: Try[String] = orderFromShipment
      .map(_.order.order_id)

    //=== get order summary ===
    println(s"\nGet order summary - ${configPara.env}")
    order_id.map { x =>
      val fileName: String = configPara.postConfig.account + "_" + x + "_" + Helper.todayDate + ".pdf"
      GetOrderSummary
        .getOrderSummary(x, configPara, fileName)
      Order_File(x, fileName)
    }
  }


  def retry[T](fib: List[Int])(f: => T): Try[T] =
    Try(f).recoverWith {
      case ex =>
        fib match {
          case Nil => Failure(ex)
          case head :: rest =>
            println("Retry Head: " + head)
            Thread.sleep(head * 1000)
            retry(rest)(f)
        }
    }


  def parseStrToJSON(str: String): Try[JsonObject] = {
    val parser = new JsonParser()
    Try {
      parser.parse(str).getAsJsonObject
    }
  }

  def checkStatusCode(jsonString: String): Int = {
    val parser: JsonParser = new JsonParser()
    val jsonobj: JsonObject = parser.parse(jsonString).getAsJsonObject
    val statusCode = jsonobj.get("code").getAsInt
    statusCode
  }

}

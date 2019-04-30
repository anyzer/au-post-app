package bran.post.utilities

import bran.post.base.request.response.Account
import bran.post.base.request.{Item, Request_Shipment, Shipments}
import bran.post.base.{Customer, response}
import bran.post.constants.Constants
import bran.post.helper.Input_Para
import bran.post.utilities.base.RequestBase
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import org.json4s.native.JsonMethods.parse

import scala.util.Try

object CreateShipment extends RequestBase {

  def createShipment(account: Account, postPara: Input_Para): Try[String] = {
    implicit val formats = DefaultFormats
    val shipmentsJson: String = write(Shipments(List(createCaseClass(account))))
    println("Shipment Request: " + shipmentsJson)

    getResponse(postPara, Constants.url.CREATE_SHIPMENT, "post", Some(shipmentsJson))
  }

  def shipments(accountStr: String): response.Shipments = {
    implicit val formats = DefaultFormats
    val shipments: response.Shipments = parse(accountStr).extract[response.Shipments]
    println(s"shipment_id = ${shipments.shipments.head.shipment_id}")
    println(s"charge to account = ${shipments.shipments.head.charge_to_account}")
    println(s"number of items = ${shipments.shipments.head.items.size}\n")
    shipments
  }

  //create shipment for creating orders
  def createCaseClass(account: Account): Request_Shipment = {
    val item1: Item = Item("100.1", "12", "12", "2.123", "blocked", "7E55", true, false, None)
    val item2: Item = Item("10", "10", "10", "1", "blocked", "7E55", true, false, None)

    val to: Customer = Customer("Blocked Carl", Some("business Name"), None, List("PO Box 123 to"),
      "Rye", "3941", "VIC", Some("0356567567"), Some("carl@gmai.co"),
      Some("please leave behind shed"), Some("1234567890"))

    val from: Customer = Customer(account.name, None, None, List("111 Bourke St"),
      "Melbourne", "3000", "VIC", None, None, None, Some("1234567890"))

    val shipment: Request_Shipment = Request_Shipment(Some("My second shipment ref"), None, "cb1234_1", "cb1234_2", from, to,
      List(item1, item2), None, None)

    shipment
  }
}

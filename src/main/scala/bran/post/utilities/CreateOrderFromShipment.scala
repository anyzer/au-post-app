package bran.post.utilities

import bran.Input_Para
import bran.post.base.request._
import bran.post.base.{request, response}
import bran.post.constants.Constants
import bran.post.utilities.CreateShipment.getResponse
import bran.post.utilities.base.RequestBase
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import org.json4s.native.JsonMethods.parse

import scala.util.Try

object CreateOrderFromShipment extends RequestBase{

  def createOrderFromShipment(shipment_id: String, postPara: Input_Para): Try[String] = {
    implicit val formats = DefaultFormats
    val createOrderFromShipment: String = write(createCaseClass(shipment_id))
    println("Create Order from Shipment Request: " + createOrderFromShipment)

    getResponse(postPara, Constants.url.CREATE_ORDER, "put", Some(createOrderFromShipment))
  }

  def orderFromShipment(orderStr: String): response.Order = {
    implicit val formats = DefaultFormats
    val orderFromShipment: response.Order = parse(orderStr).extract[response.Order]
    println(s"Order Id = ${orderFromShipment.order.order_id}")
    println(s"Order Shipment ID = ${orderFromShipment.order.shipments.head.shipment_id}")
    orderFromShipment.order.shipments.head.items.foreach{ x =>
      println("Item_Id = " + x.item_id)
      println("Article Id = " + x.tracking_details.article_id)
    }
    orderFromShipment
  }

  //create order from shipment
  def createCaseClass(shipmentid: String): request.Request_OrderFromShipment = {
    val shipment = Shipment_Id(shipmentid)
    val shipments = List(shipment)
    request.Request_OrderFromShipment("order_reference", shipments)
  }
}

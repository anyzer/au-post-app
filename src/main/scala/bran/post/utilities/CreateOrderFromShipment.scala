package bran.post.utilities

import bran.post.base.request._
import bran.post.base.response.Order
import bran.post.base.{request, response}
import bran.post.constants.Constants
import bran.post.constants.Constants.rest
import bran.post.helper.{Helper, Input_Para}
import bran.post.utilities.base.RequestBase
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write

import scala.util.Try

object CreateOrderFromShipment extends RequestBase {

  def createOrderFromShipment(shipment_id: String, postPara: Input_Para): Try[response.Order] = {
    Helper.retry(rest.FIBS) {
      implicit val formats = DefaultFormats
      val res: Try[String] = getResponse(postPara, Constants.url.CREATE_ORDER, "put", Some(write(createCaseClass(shipment_id))))
      println("create order res: " + res)

      toCaseClass[response.Order](res.get)
    }
  }


  def createOrdersFromShipments(shipments_id_list: List[String], postPara: Input_Para): Try[response.Order] = {
    Helper.retry(rest.FIBS) {
      implicit val formats = DefaultFormats
      val res: Try[String] = getResponse(postPara, Constants.url.CREATE_ORDER, "put", Some(write(createCaseClass(shipments_id_list))))
      println("create order res: " + res)

      toCaseClass[response.Order](res.get)
    }
  }


  def createCaseClass(shipmentIdList: List[String]): request.Request_OrderFromShipment = {
    val shipments: List[Shipment_Id] = shipmentIdList.map { x => Shipment_Id(x) }
    request.Request_OrderFromShipment("order_reference", shipments)
  }


  //create order from shipment
  def createCaseClass(shipmentid: String): request.Request_OrderFromShipment = {
    val shipment: Shipment_Id = Shipment_Id(shipmentid)
    val shipments: List[Shipment_Id] = List(shipment)
    request.Request_OrderFromShipment("order_reference", shipments)
  }
}

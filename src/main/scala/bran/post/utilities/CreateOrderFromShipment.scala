package bran.post.utilities

import bran.post.base.request._
import bran.post.base.{request, response}
import bran.post.constants.Constants
import bran.post.helper.Input_Para
import bran.post.utilities.base.RequestBase
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import scala.util.Try

object CreateOrderFromShipment extends RequestBase {

  def createOrderFromShipment(shipment_id: String, postPara: Input_Para): Try[response.Order] = {
    implicit val formats = DefaultFormats
    getResponse(postPara, Constants.url.CREATE_ORDER, "put",
      Some(write(createCaseClass(shipment_id))))
      .map(toCaseClass[response.Order](_))
      .flatten
  }

  //create order from shipment
  def createCaseClass(shipmentid: String): request.Request_OrderFromShipment = {
    val shipment = Shipment_Id(shipmentid)
    val shipments = List(shipment)
    request.Request_OrderFromShipment("order_reference", shipments)
  }
}

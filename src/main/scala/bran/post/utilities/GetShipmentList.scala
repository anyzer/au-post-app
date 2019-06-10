package bran.post.utilities

import bran.post.base.response.{Order_List, Shipments}
import bran.post.constants.Constants
import bran.post.helper.Input_Para
import bran.post.utilities.base.RequestBase

import scala.util.Try

object GetShipmentList extends RequestBase {

  def getShipmentList(offset: Int, postPara: Input_Para, fileName: String): Try[Shipments] = {
    val url: String = s"${Constants.url.LIST_SHIPMENTS}"
      .replace("{SHIPMENT_OFFSET}", (offset * Constants.NUMBER_OF_SHIPMENTS_PER_REQUEST + ""))
      .replace("{NUM_OF_SHIPMENTS}", (Constants.NUMBER_OF_SHIPMENTS_PER_REQUEST + ""))

    val orderList: Try[String] = getResponse(postPara, url, "GET", None)
    println("Get Order List: " + orderList)

    orderList.map(toCaseClass[Shipments](_))
  }
}

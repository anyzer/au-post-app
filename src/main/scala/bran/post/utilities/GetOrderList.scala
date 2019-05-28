package bran.post.utilities

import bran.post.base.response.Order_List
import bran.post.constants.Constants
import bran.post.helper.Input_Para
import bran.post.utilities.base.RequestBase

import scala.util.Try

object GetOrderList extends RequestBase {

  def getOrderList(offset: Int, postPara: Input_Para, fileName: String): Try[Order_List] = {
    val url: String = s"${Constants.url.LIST_ORDERS}"
      .replace("{ORDER_OFFSET}", (offset * Constants.NUMBER_OF_ORDERS_PER_REQUEST + ""))
      .replace("{NUM_OF_ORDERS}", (Constants.NUMBER_OF_ORDERS_PER_REQUEST + ""))

    val orderList: Try[String] = getResponse(postPara, url, "GET", None)
    println("Get Order List: " + orderList)

    orderList.map(toCaseClass[Order_List](_))
  }
}

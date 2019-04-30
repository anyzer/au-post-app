package bran.post.utilities

import bran.post.constants.Constants
import bran.post.helper.Input_Para
import bran.post.utilities.base.RequestBase
import scala.util.Try

object GetOrderSummary extends RequestBase {

  def getOrderSummary(order_id: String, postPara: Input_Para, fileName: String): Try[String] = {
    val url: String = s"${Constants.url.GET_ORDER_SUMMARY}"
      .replace("{ACCOUNT}", postPara.postConfig.account)
      .replace("{ORDER_ID}", order_id)

    getResponse(postPara, url, "GET_UTF", Some(fileName))
  }
}

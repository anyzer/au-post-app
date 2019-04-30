package bran.post.utilities

import java.nio.file.{Files, OpenOption, Paths, StandardOpenOption}
import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}

import bran.Input_Para
import bran.post.constants.Constants
import bran.post.utilities.base.RequestBase
import scalaj.http._

import scala.util.Try

object GetOrderSummary extends RequestBase{

  def getOrderSummary(order_id: String, postPara: Input_Para, fileName: String): Try[String] = {
    val url: String = s"${Constants.url.GET_ORDER_SUMMARY}"
      .replace("{ACCOUNT}", postPara.postConfig.account)
      .replace("{ORDER_ID}", order_id)

    getResponse(postPara, url, "GET_UTF", Some(fileName))
  }

}

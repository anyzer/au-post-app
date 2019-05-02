package bran

import bran.post.config.PostConfig
import bran.post.helper._
import org.scalatest.FunSuite

import scala.util.Try

class QuickTest extends FunSuite {

  test("sending mails") {
    ExcelHelper.getShipmentsDetails(Input_Para(Env("test"),
      Folder("shipment_details"),
      Folder("order_summary"),
      PostConfig("https://digitalapi.auspost.com.au/test",
        "Basic YzUyNmYy",
        "1003498426",
        "1003498426",
        "05500426", "00500426", "shipment_details", "order_summary")), readString)
  }

  def readString(conf: Input_Para, str: Shipment_Details): Try[Unit] = {
    Try(str.shipment_id)
  }

}


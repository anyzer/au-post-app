package bran

import java.net.URL
import scala.io.Source
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


  test("test either and fold") {
    val content: Iterator[String] = getContent(new URL("https://alvinalexander.com/scala/how-use-fold-scala-option-some-none-syntax")).fold(Iterator(_), _.getLines())
    val moreContent: Iterator[String] = getContent(new URL("http://google.com")).fold(Iterator(_), _.getLines())

    content.foreach(x => println(x))
//    println("=========================")
//    moreContent.foreach(x => println(x))
  }


  def getContent(url: URL): Either[String, Source] = {
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked")
    else Right(Source.fromURL(url))
  }

}


package bran.post.helper

import scala.io.Source
import scala.util.Try

case class Shipment_Details(shipment_id: String, column_B: String, column_C: String)

object ExcelHelper {

  def getShipmentsDetails(postPara: Input_Para, getOrderSummary: (Input_Para, Shipment_Details) => Try[Unit]): Try[Unit] = {
    val source = s"./${postPara.readFolder.path}/Shipments_${Helper.todayDate("yyyyMMdd")}.csv"
    println(s"Read from ${source}")

    Try {
      val bufferedSource = Source.fromFile(source)

      for (line <- bufferedSource.getLines().drop(1)) {
        val array: Array[String] = line.split(",").map(_.trim)
        val shipment_details: Shipment_Details = Shipment_Details(array(0), array(1), array(2))

        println(getOrderSummary(postPara, shipment_details))
      }

      bufferedSource.close()
    }
  }

}

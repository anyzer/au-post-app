package bran.post.utilities

import bran.post.base.request._
import bran.post.base.{request, response}
import bran.post.constants.Constants
import bran.post.helper.Input_Para
import bran.post.utilities.base.RequestBase
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import org.json4s.native.JsonMethods.parse
import scala.util.Try

object CreateLabel extends RequestBase {

  def createLabel(shipment_id: String, postPara: Input_Para): Try[String] = {
    implicit val formats = DefaultFormats
    val createLabel: String = write(createCaseClass(shipment_id))
    println("Print Label Request: " + createLabel)

    getResponse(postPara, Constants.url.CREATE_LABEL, "post", Some(createLabel))
  }

  def label(labelStr: String): response.PrintLabels = {
    implicit val formats = DefaultFormats
    val label: response.PrintLabels = parse(labelStr).extract[response.PrintLabels]
    println(s"message = ${label.message} - ${label.code}")
    println(s"Shipment ID = ${label.labels.head.shipment_ids.head}\n")
    label
  }

  //create label print
  def createCaseClass(shipmentid: String): request.PrintLabels = {
    val group_1: Group = Group("Parcel Post", "A4-4pp", true, 0, 0)
    val group_2: Group = Group("Express Post", "A4-4pp", false, 0, 0)
    val group_3: Group = Group("StarTrack", "A6-1pp", false, 0, 0)
    val groups: List[Group] = List(group_1, group_2, group_3)

    val preference: Preference = Preference("PRINT", groups)
    val preferences: List[Preference] = List(preference)

    val shipment_Id: Shipment_Id = Shipment_Id(shipmentid)
    val shipments: List[Shipment_Id] = List(shipment_Id)

    val printLabels: PrintLabels = request.PrintLabels(preferences, shipments)
    printLabels
  }
}

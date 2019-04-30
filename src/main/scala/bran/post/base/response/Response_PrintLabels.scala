package bran.post.base.response

case class PrintLabels(message: String, code: String, labels: List[Label])

case class Label(request_id: String, status: String, request_date: String, shipments: List[Shipment_In_Label], shipment_ids: List[String])

case class Shipment_In_Label(shipment_id: String, options: Options_Label)

case class Options_Label()
package bran.post.base.request

case class Request_OrderFromShipment(order_reference: String, shipments: List[Shipment_Id])

// Request_PrintLabels.scala
// case class Shipment_Id(shipment_id: String)
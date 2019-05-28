package bran.post.base.response

case class Order (order: OrderFromShipment)

case class OrderFromShipment (order_id: String,
                              order_reference: String,
                              order_creation_date: String,
                              order_summary: Order_Summary,
                              shipments: List[Response_Shipment],
                              payment_method: String)

case class Order_Summary (total_cost: Double, total_cost_ex_gst: Double, total_gst: Double, status: String,
                          tracking_summary: Order_Tracking_Summary, number_of_shipments: Int, number_of_pickups: Int,
                          number_of_items: Int, dangerous_goods_included: Boolean, total_weight: Double,
                          shipping_methods: Option[Shipping_Methods])

case class Order_Tracking_Summary (Sealed: Int)

case class Shipping_Methods (`7E55`: Option[Int])

//Shipments is defined in Response_Shipment.scala
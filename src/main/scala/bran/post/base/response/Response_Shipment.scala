package bran.post.base.response

import bran.post.base.Customer
import bran.post.base.request.response.Price

case class Shipments(shipments: List[Response_Shipment])

case class Response_Shipment(
                     shipment_id: String,
                     shipment_reference: Option[String],
                     shipment_creation_date: String,
                     customer_reference_1: Option[String],
                     customer_reference_2: Option[String],
                     sender_references: Option[List[String]],
                     from: Option[Customer],
                     to: Option[Customer],
                     items: List[Item],
                     options: Option_Shipment,
                     shipment_summary: Shipment_Summary,
                     order_id: Option[String],
                     movement_type: String,
                     charge_to_account: String
                   )

case class Item(
                 weight: Double,
                 height: Option[Double],
                 length: Option[Double],
                 width: Option[Double],
                 contains_dangerous_goods: Option[Boolean],
                 authority_to_leave: Boolean,
                 safe_drop_enabled: Boolean,
                 allow_partial_delivery: Boolean,
                 item_id: String,
                 item_reference: String,
                 tracking_details: Tracking_Details,
                 product_id: String,
                 item_summary: Item_Summary,
                 item_contents: List[Item_Content],
                 label: Option[Order_Item_Lable],
                 postage_details: Option[Postage_Details]
               )

case class Postage_Details(price: Price)

case class Order_Item_Lable(label_url: String, status: String, errors: List[String], label_creation_date: String)

case class Item_Summary(total_cost: Double, total_cost_ex_gst: Double, total_gst: Double, status: String)

case class Tracking_Details(article_id: String, consignment_id: String, barcode_id: Option[String])

case class Option_Shipment()

case class Shipment_Summary(total_cost: Double, total_cost_ex_gst: Double, fuel_surcharge: Double,
                            total_gst: Double, status: String, tracking_summary: Tracking_Summary,
                            number_of_items: Int)

case class Tracking_Summary(Created: Option[Int], Sealed: Option[Int])

case class Item_Content()
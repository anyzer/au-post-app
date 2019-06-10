package bran.post.base

case class Customer(
                     name: String,
                     business_name: Option[String],
                     `type`: Option[String],
                     lines: List[String],
                     suburb: String,
                     postcode: String,
                     state: String,
                     phone: Option[String],
                     email: Option[String],
                     delivery_instructions: Option[String],
                     apcn: Option[String],
                     country: Option[String]
                   )

case class Shipments_Details(shipment_id: String,
                             shipment_status: String,
                             items_details: List[Items_Details]
                            )

case class Items_Details(item_id: String,
                         consignment_id: String,
                        )
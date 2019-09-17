package bran.post.base.request

import bran.post.base.Customer

case class Shipments (shipments: List[Request_Shipment])

case class Request_Shipment(
                             shipment_reference: Option[String],
                             sender_references: Option[String],
                             customer_reference_1: String,
                             customer_reference_2: String,
                             from: Customer,
                             to: Customer,
                             items: List[Item_Req],
                             goods_descriptions: Option[String],
                             email_tracking_enabled: Option[Boolean]
                   )

case class Item_Req(
                 length: String,
                 height: String,
                 width: String,
                 weight: String,
                 item_reference: String,
                 product_id: String,
                 authority_to_leave: Boolean,
                 allow_partial_delivery: Boolean,
                 features: Option[Features]
               )

case class Features(TRANSIT_COVER: TRANSIT_COVER)

case class TRANSIT_COVER(attributes: Attributes)

case class Attributes(cover_amount: BigInt)

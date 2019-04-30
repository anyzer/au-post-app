package bran.post.base.request

case class PrintLabels(preferences: List[Preference], shipments: List[Shipment_Id])

case class Preference(`type`: String, groups: List[Group])

case class Group(group: String, layout: String, branded: Boolean, left_offset: Integer, top_offset: Integer)

case class Shipment_Id(shipment_id: String)
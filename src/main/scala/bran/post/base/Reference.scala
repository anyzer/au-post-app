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
                     apcn: Option[String]
                   )
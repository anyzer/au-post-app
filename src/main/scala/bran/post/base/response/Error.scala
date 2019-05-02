package bran.post.base.response

case class Errors (errors: List[Error])

case class Error (code: String, name: String, message: String, field: String, context: Context)

case class Context (item_id: String, shipment_id: String)


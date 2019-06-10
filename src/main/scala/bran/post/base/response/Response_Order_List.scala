package bran.post.base.response

case class Order_List(pagination: Pagination, orders: List[OrderFromShipment])

case class Pagination(total_number_of_records: Int,
                      number_of_records_per_page: Int,
                      current_page_number: Int)

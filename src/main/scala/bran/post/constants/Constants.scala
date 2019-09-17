package bran.post.constants

object Constants {

  object url {
    val GET_ACCOUNT = "/shipping/v1/accounts/"

    val CREATE_SHIPMENT = "/shipping/v1/shipments"

    val GET_ORDER_SUMMARY =  "/shipping/v1/accounts/{ACCOUNT}/orders/{ORDER_ID}/summary"

    val CREATE_LABEL = "/shipping/v1/labels"

    val CREATE_ORDER = "/shipping/v1/orders"

    val LIST_ORDERS = "/shipping/v1/orders?offset={ORDER_OFFSET}&number_of_orders={NUM_OF_ORDERS}"

    val LIST_SHIPMENTS = "/shipping/v1/shipments?offset={SHIPMENT_OFFSET}&number_of_shipments={NUM_OF_SHIPMENTS}"
  }

  object rest {
    val TIME_OUT = 20000

//    val fibs: List[Int] = List(1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711)
    val FIBS: List[Int] = List(1, 2, 3, 5, 8)
  }

  val NUMBER_OF_ORDERS_PER_REQUEST = 500
  val NUMBER_OF_SHIPMENTS_PER_REQUEST = 99000

  val LIST_OF_CONSIGMENT_ID = List("111JD5250352", "111JD5233613", "111JD5232471", "111JD5230823", "111JD5230806")

}

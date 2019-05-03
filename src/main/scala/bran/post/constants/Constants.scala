package bran.post.constants

object Constants {

  object url {
    val GET_ACCOUNT = "/shipping/v1/accounts/"

    val CREATE_SHIPMENT = "/shipping/v1/shipments"

    val GET_ORDER_SUMMARY =  "/shipping/v1/accounts/{ACCOUNT}/orders/{ORDER_ID}/summary"

    val CREATE_LABEL = "/shipping/v1/labels"

    val CREATE_ORDER = "/shipping/v1/orders"
  }

  object rest {
    val TIME_OUT = 20000
  }
}

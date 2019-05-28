package bran.post.base.request.response

case class Account(
                    account_number: String,
                    name: String,
                    valid_from: String,
                    valid_to: String,
                    expired: Boolean,
                    addresses: Option[List[Address]],
                    details: Option[Details],
                    postage_products: List[Product],
                    returns_products: List[Product],
                    merchant_location_id: String,
                    credit_blocked: Boolean
                  )

//{"account_number":"0007425166","name":"IDS COMPANY P/L","valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"addresses":[{"type":"MERCHANT_LOCATION","lines":["1 Tabilk Court"],"suburb":"WANTIRNA","postcode":"3152","state":"VIC","country":"AU"}],"details":{"lodgement_postcode":"3061","email_address":"brian.lau@idscompany.com.au","is_location":"false","is_sscc_non_standard_barcode_enabled":"false","work_centre_code":"320549","abn":"75167640575","work_centre_name":"SOMERTON BUSINESS HUB DWS","acn":"167640575","contact_number":"","account_source":"AUSPOST"},"postage_products":[{"type":"INTL ECONOMY W SOD/ REGD POST","product_id":"RPI8","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":167},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":2.5,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"dangerous_goods_allowed":false}},{"type":"INTL STANDARD/PACK & TRACK","product_id":"PTI8","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":167},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":2.5,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":false,"dangerous_goods_allowed":false}},{"type":"INT'L STANDARD WITH SIGNATURE","product_id":"PTI7","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":167},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":2.5,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"dangerous_goods_allowed":false}},{"type":"INTL EXPRESS MERCH/ECI MERCH","product_id":"ECM8","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":167},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":2.5,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"dangerous_goods_allowed":false}},{"type":"INTL EXPRESS DOCS/ECI DOCS","product_id":"ECD8","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":167},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":2.5,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"dangerous_goods_allowed":false}},{"type":"INTL ECONOMY/AIRMAIL PARCELS","product_id":"AIR8","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":167},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":2.5,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":false,"dangerous_goods_allowed":false}},{"type":"PARCEL POST + SIGNATURE","group":"Parcel Post","product_id":"7D55","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":true,"max_item_count":0,"cubing_factor":250},"authority_to_leave_threshold":500.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":1,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"authority_to_leave_option":true,"dangerous_goods_allowed":true},"shipment_features":{"DELIVERY_TIMES":{"type":"DELIVERY_TIMES","price":{"calculated_price":0.00,"calculated_gst":0},"bundled":false},"DELIVERY_DATE":{"type":"DELIVERY_DATE","price":{"calculated_price":0.00,"calculated_gst":0},"bundled":false}}},{"type":"EXPRESS POST + SIGNATURE","group":"Express Post","product_id":"3J05","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":true,"max_item_count":1,"cubing_factor":250},"authority_to_leave_threshold":500.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":1,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"authority_to_leave_option":true,"dangerous_goods_allowed":true},"shipment_features":{"DELIVERY_TIMES":{"type":"DELIVERY_TIMES","price":{"calculated_price":0.00,"calculated_gst":0},"bundled":false},"DELIVERY_DATE":{"type":"DELIVERY_DATE","price":{"calculated_price":0.00,"calculated_gst":0},"bundled":false}}}],"returns_products":[{"type":"EXPRESS EPARCEL POST RETURNS","group":"Express Post","product_id":"XPR","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":250},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":1,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"authority_to_leave_option":true,"dangerous_goods_allowed":true}},{"type":"EPARCEL POST RETURNS","group":"Parcel Post","product_id":"PR","contract":{"valid_from":"2015-01-15","valid_to":"2020-03-31","expired":false,"volumetric_pricing":false,"max_item_count":0,"cubing_factor":250},"authority_to_leave_threshold":0.0,"features":{"TRANSIT_COVER":{"type":"TRANSIT_COVER","attributes":{"rate":1,"included_cover":0,"maximum_cover":5000},"price":{"calculated_price":0.00,"calculated_gst":0.00},"bundled":false}},"options":{"signature_on_delivery_option":true,"authority_to_leave_option":true,"dangerous_goods_allowed":true}}],"merchant_location_id":"ZX6","credit_blocked":false}

case class Address(`type`: String, lines: List[String], suburb: Option[String], postcode: String, state: String, apcn: Option[String], name: Option[String], country: String)

case class Details(lodgement_postcode: String, email_address: String, is_location: String, is_sscc_non_standard_barcode_enabled: String,
                   work_centre_code: String, abn: Option[String], work_centre_name: String, acn: String, contact_number: String, account_source: String)

case class Product(`type`: String, group: Option[String], product_id: String, contract: Contract, authority_to_leave_threshold: Double,
                   features: Features, options: Options, shipment_features: Option[Shipment_Features])

case class Contract(valid_from: String, valid_to: String, expired: Boolean, volumetric_pricing: Boolean, max_item_count: BigInt, cubing_factor: BigInt)

case class Features(TRANSIT_COVER: Option[TRANSIT_COVER])

case class TRANSIT_COVER(`type`: Option[String], attributes: Attributes, price: Price, bundled: Boolean)

case class Attributes(rate: Double, included_cover: BigInt, maximum_cover: BigInt)

case class Price(calculated_price: Double,
                 calculated_price_ex_gst: Option[Double],
                 calculated_gst: Double)

case class Options(signature_on_delivery_option: Boolean, dangerous_goods_allowed: Boolean, authority_to_leave_option: Option[Boolean])

case class Shipment_Features(DELIVERY_TIMES: DELIVERY_TIMES, DELIVERY_DATE: DELIVERY_DATE)

case class DELIVERY_TIMES(`type`: String, price: Price, bundled: Boolean)

case class DELIVERY_DATE(`type`: String, price: Price, bundled: Boolean)

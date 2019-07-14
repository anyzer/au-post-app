package bran.run

import bran.post.base.Shipments_Details
import bran.post.helper.{ExcelHelper, Helper, Input_Para}

import scala.util.{Failure, Success, Try}

object Run_Helper {

  def getShipmentList(configPara: Option[Input_Para]): Try[List[Shipments_Details]] = {
    // get shipment lists, in shipments JSON can find
    val shipmentsDetails: Try[List[Shipments_Details]] = Helper.get_shipments_list(configPara.get)
      .map { x => Helper.getShipmentsDetails(x) }

    if (shipmentsDetails.isFailure) {
      println("Cannot get List Shipments, may because network issue or authentication problem -> Exit(1)")
      System.exit(1)
    }

    shipmentsDetails
  }


  def checkShipmentsForEveryConsignment(configPara: Option[Input_Para], shipmentsDetails: Try[List[Shipments_Details]]): String = {
    val destination = s"./${configPara.get.readFolder.path}/Consignment_${Helper.todayDate("yyyyMMdd")}_Shipment.csv"

    val shipments_list: Try[Unit] = ExcelHelper.checkShipmentsForAllConsignments(configPara.get, shipmentsDetails.get, Helper.compareConsignment)

    shipments_list match {
      case Success(s) => {
        ExcelHelper.checkConsignmentsAndShipments(destination) match {
          case Success(0) => println("All consignments find corresponding shipments\n")
          case _ => println(s"TODO: Please check file ${destination} as \n(1) one ore more Consignments do not have corresponding shipments; \n(2) shipment(s) status in invalid")
            println(s"\nTODO: Search for 'ERROR' in ${destination}, and update the source file Consignment_${Helper.todayDate("yyyyMMdd")}.csv accordingly")
            System.exit(1)
        }
      }
      case Failure(f) => {
        println("Error to find shipments id from consignments: " + f.getMessage)
        println(s"Put Consignments Id in a file with the following naming convention: Consignment_${Helper.todayDate("yyyyMMdd")}.csv")
        System.exit(1)
      }
    }

    destination
  }
}

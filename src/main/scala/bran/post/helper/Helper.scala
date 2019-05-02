package bran.post.helper

import java.time.{LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter

import bran.post.config.PostConfig
import com.google.gson.{JsonObject, JsonParser}

import scala.util.Try

case class Env(env: String)

case class Folder(path: String)

case class Input_Para(env: Env, readFolder: Folder, writeFolder: Folder, postConfig: PostConfig)

case class Account_Details(account_number: String, name: String, address_lines: List[String],
                           address_suburb: Option[String], address_state: String, address_country: String)

object Helper {

  def getConfig(args: Array[String]): Option[Input_Para] = {
    if (args.length == 3 && (args(0) == "test" || args(0) == "prod"))
      Option(Input_Para(Env(args(0)), Folder(args(1)), Folder(args(2)), PostConfig.get(Env(args(0)))))
    else if (args.length == 1 && (args(0) == "test" || args(0) == "prod")) {
      val config: PostConfig = PostConfig.get(Env(args(0)))
      Option(Input_Para(Env(args(0)), Folder(config.read_path), Folder(config.write_path), config))
    }
    else {
      println("Sample command: <prod read/folder/ write/folder/> <test read/folder/ write/folder/> \n\n" +
        "There should be either 1 or 3 params, and first parameter should be either 'test' or 'prod'\n\n"
      )
      None
    }
  }

  def todayDate: String = LocalDateTime.now(ZoneId.of("Australia/Sydney"))
    .format(DateTimeFormatter
      .ofPattern("yyyyMMdd_HHmmss"))

  def parseStrToJSON(str: String): Try[JsonObject] = {
    val parser = new JsonParser()
    Try{
      parser.parse(str).getAsJsonObject
    }
  }

  def checkStatusCode(jsonString: String) : Int = {
    val parser: JsonParser = new JsonParser()
    val jsonobj: JsonObject = parser.parse(jsonString).getAsJsonObject
    val statusCode = jsonobj.get("code").getAsInt
    statusCode
  }

}

package bran.post.helper

import com.google.gson.{JsonObject, JsonParser}

import scala.util.Try

object JSONHelper {

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

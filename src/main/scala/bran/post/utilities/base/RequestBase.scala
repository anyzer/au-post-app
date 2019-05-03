package bran.post.utilities.base

import java.nio.file.{Files, Paths, StandardOpenOption}

import bran.post.constants.Constants.rest
import bran.post.helper.Input_Para
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import scalaj.http.{Http, HttpConstants, HttpOptions}

import scala.util.{Failure, Try}

trait RequestBase {

  def getResponse(postPara: Input_Para, sub_url: String, method: String, request: Option[String]): Try[String] = {
    implicit val formats = DefaultFormats
    val url = s"${postPara.postConfig.base_url}${sub_url}"
    val requestHeaders = Map(
      "Authorization" -> postPara.postConfig.authorization,
      "Content-Type" -> "application/json",
      "account-number" -> postPara.postConfig.account
    )

    method.toUpperCase match {
      case "POST" => Try(Http(url).postData(request.get).headers(requestHeaders)
        .option(HttpOptions.readTimeout(rest.TIME_OUT)).asString.body)
      case "PUT" => Try(Http(url).put(request.get).headers(requestHeaders)
        .option(HttpOptions.readTimeout(rest.TIME_OUT)).asString.body)
      case "GET" => Try(Http(url).headers(requestHeaders)
        .option(HttpOptions.readTimeout(rest.TIME_OUT)).asString.body)
      case "GET_UTF" => Try {
        val body: Array[Byte] = Http(url).charset(HttpConstants.utf8).headers(requestHeaders)
          .option(HttpOptions.readTimeout(rest.TIME_OUT)).asBytes.body
        Files.write(Paths.get("./" + postPara.writeFolder.path + "/" + request.get), body, StandardOpenOption.CREATE)
        request.get
      }
      case _ => Failure(new Exception("Operations options: POST, PUT, GET, GET_UTF"))
    }
  }

  def toCaseClass[T: Manifest](response: String): Try[T] = {
    Try {
      implicit val formats = DefaultFormats
      val res: T = parse(response).extract[T]
      res
    }
  }
}

package bran.post.utilities.base

import java.nio.file.{Files, Paths, StandardOpenOption}
import java.util.UUID

import bran.Input_Para
import org.json4s.DefaultFormats
import scalaj.http.{Http, HttpConstants, HttpOptions, HttpResponse}

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
      case "POST" => Try(Http(url)
        .postData(request.get)
        .headers(requestHeaders)
        .option(HttpOptions.readTimeout(10000))
        .asString.body)
      case "PUT" => Try(Http(url)
        .put(request.get)
        .headers(requestHeaders)
        .option(HttpOptions.readTimeout(10000))
        .asString.body)
      case "GET" => Try(Http(url)
        .headers(requestHeaders)
        .option(HttpOptions.readTimeout(10000))
        .asString.body)
      case "GET_UTF" => Try {
        val body = Http(url)
          .charset(HttpConstants.utf8)
          .headers(requestHeaders)
          .option(HttpOptions.readTimeout(10000))
          .asBytes.body
        val temp_file_name = UUID.randomUUID().toString + "_" + request.get
        Files.write(Paths.get("/order_summary/" + request.get), body, StandardOpenOption.CREATE)
        temp_file_name
      }
      case _ => Failure(new Exception("Operations options: POST, PUT, GET, GET_UTF"))
    }
  }

}

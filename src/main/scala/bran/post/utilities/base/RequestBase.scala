package bran.post.utilities.base

import java.nio.file.{Files, Paths, StandardOpenOption}

import bran.post.base.response.ResErrors
import bran.post.constants.Constants.rest
import bran.post.helper.{Helper, Input_Para}
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import scalaj.http.{Http, HttpConstants, HttpOptions}

import scala.util.{Failure, Success, Try}

trait RequestBase {

  def getResponse(postPara: Input_Para, sub_url: String, method: String, request: Option[String]): Try[String] = {
    implicit val formats = DefaultFormats
    val url = s"${postPara.postConfig.base_url}${sub_url}"
    val requestHeaders: Map[String, String] = Map(
      "Authorization" -> postPara.postConfig.authorization,
      "Content-Type" -> "application/json",
      "account-number" -> postPara.postConfig.account
    )

    method.toUpperCase match {
      case "POST" => getPost(url, request, requestHeaders, Some(postPara))
      case "PUT" => getPut(url, request, requestHeaders, Some(postPara))
      case "GET" => getGet(url, request, requestHeaders, Some(postPara))
      case "GET_UTF" => getGetUTF(url, request, requestHeaders, Some(postPara))
      case _ => Failure(new Exception("Operations options: POST, PUT, GET, GET_UTF"))
    }
  }

  def toCaseClass[T: Manifest](response: String): T = {
    implicit val formats = DefaultFormats
    val res: T = parse(response).extract[T]
    res
  }


  //  ============= private method =================


  private def getGetUTF(url: String, request: Option[String], requestHeaders: Map[String, String], postPara: Option[Input_Para]): Try[String] = {
    println(s"Get UTF from ${url}")
    Try {
      val body: Array[Byte] = Http(url)
        .charset(HttpConstants.utf8)
        .headers(requestHeaders)
        .option(HttpOptions.readTimeout(rest.TIME_OUT))
        .asBytes.body

      Files.write(Paths.get("./" + postPara.get.writeFolder.path + "/" + request.get), body, StandardOpenOption.CREATE)
      request.get
    }
  }

  private def getPut(url: String, request: Option[String], requestHeaders: Map[String, String], postPara: Option[Input_Para]): Try[String] = {
    println(s"Put to ${url}")
    Try(Http(url)
      .put(request.get)
      .headers(requestHeaders)
      .option(HttpOptions
        .readTimeout(rest.TIME_OUT))
      .asString
      .body)
  }

  private def getPost(url: String, request: Option[String], requestHeaders: Map[String, String], postPara: Option[Input_Para]): Try[String] = {
    println(s"Post to ${url}")
    Try(Http(url)
      .postData(request.get)
      .headers(requestHeaders)
      .option(HttpOptions
        .readTimeout(rest.TIME_OUT))
      .asString
      .body)
  }

  private def getGet(url: String, request: Option[String], requestHeaders: Map[String, String], postPara: Option[Input_Para]): Try[String] = {
    println(s"Get from ${url}")
    Try(Http(url)
      .headers(requestHeaders)
      .option(HttpOptions
        .readTimeout(rest.TIME_OUT))
      .asString
      .body)
  }

}

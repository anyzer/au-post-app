package bran.post.utilities

import bran.post.base.request.response.Account
import bran.post.constants.Constants
import bran.post.helper.Input_Para
import bran.post.utilities.base.RequestBase
import org.json4s._
import org.json4s.native.JsonMethods._
import scala.util.Try

object GetAccount extends RequestBase {

  def getAccount(postPara: Input_Para): Try[String] = {
    val sub_url = s"${Constants.url.GET_ACCOUNT}${postPara.postConfig.account}"
    getResponse(postPara, sub_url, "GET", None)
  }

  def account(accountStr: String): Account = {
    implicit val formats = DefaultFormats
    val account: Account = parse(accountStr).extract[Account]
    println(s"account_number = ${account.account_number}")
    println(s"name = ${account.name}\n")
    account
  }
}

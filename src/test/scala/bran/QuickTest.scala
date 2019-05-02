package bran

import bran.post.helper.MailerService
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.services.gmail.Gmail
import org.scalatest.FunSuite

class QuickTest extends FunSuite {

  test("sending mails") {
    MailerService.sendMessage("Do not reply - Order ", "\nFYI\n", "1003498426_TB00079094_20190430_225838.pdf")
  }

}


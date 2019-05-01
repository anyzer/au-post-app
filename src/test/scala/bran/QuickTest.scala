package bran

import bran.post.helper.MailerService
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.services.gmail.Gmail
import org.scalatest.FunSuite

class QuickTest extends FunSuite {

  test("sending mails") {
    MailerService.sendMessage("Do not reply - Order ", "Please find the attachment below")
  }

}


package bran.post.helper

import java.util.Properties

import javax.mail._
import javax.mail.internet.{InternetAddress, MimeMessage}
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

import scala.io.Source
import scala.util.Try

case class APICredentials(project_id: String, auth_uri: String, token_uri: String,
                          auth_provider: String, client_secret: String, emails: String)

object MailerService {

  def sendMessage(subject: String, content: String): Try[Unit] = {
    implicit val formats = DefaultFormats
    val credential: APICredentials = parse(Source.fromFile("src/main/resources/credentials.json").mkString).extract[APICredentials]
    val username = credential.auth_provider
    val password = credential.client_secret.split("-")(1)

    val prop = new Properties()
    prop.put("mail.smtp.host", "smtp.gmail.com")
    prop.put("mail.smtp.port", "465")
    prop.put("mail.smtp.auth", "true")
    prop.put("mail.smtp.socketFactory.port", "465")
    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")

    val session: Session = Session.getInstance(prop, new Authenticator() {
      override protected def getPasswordAuthentication = new PasswordAuthentication(username, password)
    })

    Try {
      val message = new MimeMessage(session)
      message.setFrom(new InternetAddress("not-reply@gmail.com"))
      message.setRecipients(Message.RecipientType.TO, credential.emails)
      message.setSubject(subject)

      message.setText(content)


      Transport.send(message)
    }
  }

}
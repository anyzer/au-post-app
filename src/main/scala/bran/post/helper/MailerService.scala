package bran.post.helper

import java.util.Properties

import javax.activation.{DataHandler, FileDataSource}
import javax.mail._
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

import scala.io.Source
import scala.util.Try

case class APICredentials(project_id: String, auth_uri: String, token_uri: String,
                          auth_provider: String, client_secret: String, emails: String)

object MailerService {

  def sendMessage(subject: String, content: String, file: String): Try[Unit] = {
    implicit val formats = DefaultFormats
    val credential: APICredentials = parse(Source.fromFile("./credentials/credentials.json").mkString).extract[APICredentials]
    val username: String = credential.auth_provider
    val password: String = credential.client_secret.split("-")(1)

    val prop: Properties = new Properties()
    prop.put("mail.smtp.host", "smtp.gmail.com")
    prop.put("mail.smtp.port", "465")
    prop.put("mail.smtp.auth", "true")
    prop.put("mail.smtp.socketFactory.port", "465")
    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")

    val session: Session = Session.getInstance(prop, new Authenticator() {
      override protected def getPasswordAuthentication = new PasswordAuthentication(username, password)
    })

    Try {
      val message: MimeMessage = new MimeMessage(session)
      message.setFrom(new InternetAddress("not-reply@gmail.com"))
      message.setRecipients(Message.RecipientType.TO, credential.emails)
      message.setSubject(subject)

      val messageBodyPart: MimeBodyPart = new MimeBodyPart()
      messageBodyPart.setText("Please find the attachment below\n" + content)

      val messageBodyPart_attach: MimeBodyPart = new MimeBodyPart()
      val filename: String = "./order_summary/" + file
      val source: FileDataSource = new FileDataSource(filename)
      messageBodyPart_attach.setDataHandler(new DataHandler(source))
      messageBodyPart_attach.setFileName(file)

      val multipart = new MimeMultipart()
      multipart.addBodyPart(messageBodyPart)
      multipart.addBodyPart(messageBodyPart_attach)
      message.setContent(multipart)

      Transport.send(message)
    }
  }
}
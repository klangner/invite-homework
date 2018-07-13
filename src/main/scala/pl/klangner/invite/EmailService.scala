package pl.klangner.invite

import org.slf4j.LoggerFactory

/**
  * Mockup for email service
  */
object EmailService {

  private val Log = LoggerFactory.getLogger(getClass.getName)

  def sendInvitation(sender: String, email: String, invitationId: String): Unit = {
    val subject = s"Invitation from $sender"
    val body = s"Hi $email, $sender send you invitation with id: $invitationId"
    send(email, subject, body)
  }

  def send(email: String, subject: String, body: String): Unit = {
    Log.info(s"Send email to: $email")
    Log.info(s"subject: $subject")
    Log.info(s"body: $body")
  }
}

package pl.klangner.invite

/**
  * Application model
  */
object Models {

  sealed trait Status
  case object NotConfirmed extends Status
  case object Confirmed extends Status
  case object Declined extends Status

  case class Invitation(id: String, invitee: String, email: String, status: Status)

  case class Invite(invitee: String, email: String)
}

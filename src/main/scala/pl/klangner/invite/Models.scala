package pl.klangner.invite

import spray.json.{DefaultJsonProtocol, JsObject, JsString, JsValue, RootJsonFormat}

/**
  * Application model
  */
object Models {

  sealed trait Status
  case object NotConfirmed extends Status
  case object Confirmed extends Status
  case object Declined extends Status

  case class Invitation(id: String, invitee: String, email: String, status: Status)

  case class InvitationParams(invitee: String, email: String)
}

object ModelsJsonProtocol extends DefaultJsonProtocol {

  import Models._

  /** Invitation params json format converter */
  implicit val invitationParamsFormat: RootJsonFormat[InvitationParams] = jsonFormat2(InvitationParams)

  /** Invitation json format converters */
  implicit object InvitationJsonFormat extends RootJsonFormat[Invitation] {
    def write(inv: Invitation) =
      JsObject(
        "id" -> JsString(inv.id),
        "invitee" -> JsString(inv.invitee),
        "email" -> JsString(inv.email),
        "status" -> JsString(inv.status.toString)
      )

    def read(value: JsValue): Invitation = value match {
      case JsObject(fs) =>
        val id: String = fs.get("id").map(stringFromValue).getOrElse("")
        val invitee: String = fs.get("invitee").map(stringFromValue).getOrElse("")
        val email: String = fs.get("email").map(stringFromValue).getOrElse("")
        val status: Status = fs.get("status").map {
          case JsString("Confirmed") => Confirmed
          case JsString("Declined") => Declined
          case _ => NotConfirmed
        }.getOrElse(NotConfirmed)
        Invitation(id, invitee, email, status)
      case _ => Invitation("", "", "", NotConfirmed)
    }
  }

  /** Helper function for json string conversion */
  private def stringFromValue(jsVal: JsValue): String = jsVal match {
    case JsString(str) => str
    case v: JsValue => v.toString
  }

}
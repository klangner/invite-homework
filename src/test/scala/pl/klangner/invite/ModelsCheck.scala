package pl.klangner.invite

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll
import pl.klangner.invite.Models._
import pl.klangner.invite.ModelsJsonProtocol._
import spray.json._


/**
  * Property testing for Models json serialization
  */
object ModelsCheck extends Properties("Models") {

  private val invitationParamsGen = for {
    invitee <- Gen.alphaStr
    email <- Gen.alphaStr
  } yield InvitationParams(invitee, email)

  private val statusGen = Gen.oneOf(NotConfirmed, Confirmed, Declined)

  private val invitationGen = for {
    id <- Gen.identifier
    invitee <- Gen.alphaStr
    email <- Gen.alphaStr
    status <- statusGen
  } yield Invitation(id, invitee, email, status)


  /** InvitationParams serialized to json and then parsed back should be the same */
  property("InvitationParams") = forAll(invitationParamsGen) { params: InvitationParams =>
    val source: String = params.toJson.prettyPrint
    source.parseJson.convertTo[InvitationParams] == params
  }

  /** Invitation serialized to json and then parsed back should be the same */
  property("Invitation") = forAll(invitationGen) { invitation: Invitation =>
    val source: String = invitation.toJson.prettyPrint
    source.parseJson.convertTo[Invitation] == invitation
  }

}
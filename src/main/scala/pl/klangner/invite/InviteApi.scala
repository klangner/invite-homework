package pl.klangner.invite

import akka.http.scaladsl.model.{HttpEntity, HttpResponse, MediaTypes, StatusCodes}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import org.slf4j.LoggerFactory
import pl.klangner.invite.Models.{Invitation, InvitationParams}
import pl.klangner.invite.ModelsJsonProtocol._
import spray.json._


class InviteApi(storage: Storage) {

  private val Log = LoggerFactory.getLogger(getClass.getName)

  /** List all invitations */
  def listInvitations(): StandardRoute = {
    Log.info("List invitations")
    val invitations = Seq[Invitation]()
    complete(HttpResponse(
      StatusCodes.OK,
      entity = HttpEntity(MediaTypes.`application/json`, invitations.toJson.compactPrint)
    ))
  }

  /** Add invitation to the database and send email to the invited person */
  def addInvitation(params: InvitationParams): StandardRoute = {
    Log.info("Add invitation")

    val invitation = storage.addInvitation(params.invitee, params.email)

    EmailService.sendInvitation(invitation.invitee, invitation.email, invitation.id)

    complete(HttpResponse(
      StatusCodes.Created,
      entity = HttpEntity(MediaTypes.`application/json`, invitation.toJson.compactPrint)
    ))
  }

  /** Confirm invitation with the given id */
  def confirm(id: String): StandardRoute = {
    Log.info("Confirm invitation")

    complete(HttpResponse(StatusCodes.OK, entity = s"""{ "id":"$id", "invitee": "John Smith", "email": "john@smith.mx" }"""))
  }

  /** Decline invitation with the given id */
  def decline(id: String): StandardRoute = {
    Log.info("Decline invitation")

    complete(HttpResponse(StatusCodes.OK, entity = """{ "id":"$id", "invitee": "John Smith", "email": "john@smith.mx" }"""))
  }

}
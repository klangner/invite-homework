package pl.klangner.invite

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import org.slf4j.LoggerFactory

object InviteApi {

  private val Log = LoggerFactory.getLogger(getClass.getName)

  def listInvitations(): StandardRoute = {
    Log.info("List invitations")
    complete("[]")
  }

  def addInvitation(): StandardRoute = {
    Log.info("Add invitation")

    complete(HttpResponse(StatusCodes.Created, entity = """{ "id":"1", "invitee": "John Smith", "email": "john@smith.mx" }"""))
  }

  def confirm(id: String): StandardRoute = {
    Log.info("Add invitation")

    complete(HttpResponse(StatusCodes.OK, entity = s"""{ "id":"$id", "invitee": "John Smith", "email": "john@smith.mx" }"""))
  }

  def decline(id: String): StandardRoute = {
    Log.info("Add invitation")

    complete(HttpResponse(StatusCodes.OK, entity = """{ "id":"$id", "invitee": "John Smith", "email": "john@smith.mx" }"""))
  }

}
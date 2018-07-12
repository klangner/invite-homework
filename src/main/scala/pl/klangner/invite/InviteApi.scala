package pl.klangner.invite

import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import org.slf4j.LoggerFactory

object InviteApi {

  private val Log = LoggerFactory.getLogger(getClass.getName)

  def listInvitations(): StandardRoute = {
    Log.info("List invitations")
    complete("Ok")
  }

  def addInvitation(): StandardRoute = {
    Log.info("Add invitation")
    complete("Ok")
  }

}
package pl.klangner.invite

import pl.klangner.invite.Models.{Confirmed, Declined, Invitation, NotConfirmed}

import scala.collection.mutable

/**
  * This is very naive storage implementation.
  * It should be implemented as database or as Akka Actor
  */
object Storage {

  sealed trait ErrorCodes

  case object InvitationNotFound extends ErrorCodes

}

class Storage {

  import Storage._

  /** In normal application this will be stored in the database */
  private var database = mutable.HashMap[String, Invitation]()


  /**
    * Add new invitation. Several invitation with the same invitee and email can be added since
    * invitation is recognized by its id
    */
  def addInvitation(invitee: String, email: String): Invitation = {
    val invitation = Invitation(nextId(), invitee, email, NotConfirmed)
    database.put(invitation.id, invitation)
    invitation
  }

  /** Get list of all invitations already in the database */
  def invitations(): Seq[Invitation] = {
    database.values.toList
  }

  /**
    * Confirm invitation
    */
  def confirm(invitationId: String): Either[ErrorCodes, Invitation] = {
    database.get(invitationId) match {
      case Some(invitation) =>
        val newInvitation = Invitation(invitation.id, invitation.invitee, invitation.email, Confirmed)
        database.put(newInvitation.id, newInvitation)
        Right(newInvitation)
      case None =>
        Left(InvitationNotFound)
    }
  }

  /**
    * Decline invitation
    */
  def decline(invitationId: String): Either[ErrorCodes, Invitation] = {
    database.get(invitationId) match {
      case Some(invitation) =>
        val newInvitation = Invitation(invitation.id, invitation.invitee, invitation.email, Declined)
        database.put(newInvitation.id, newInvitation)
        Right(newInvitation)
      case None =>
        Left(InvitationNotFound)
    }
  }

  /**
    * This is very naive implementation but enough here since we only add new records
    */
  private def nextId(): String = {
    val id = database.size + 1
    id.toString
  }
}

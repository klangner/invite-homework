package pl.klangner.invite

import org.scalatest.{FlatSpec, Matchers}
import pl.klangner.invite.Models.{Confirmed, Declined, Invitation, NotConfirmed}
import pl.klangner.invite.Storage.InvitationNotFound

/**
  * Tests for Storage object
  */
class StorageTest extends FlatSpec with Matchers {

  "Storage" should "be empty at start" in {
    val storage = new Storage()
    storage.invitations() shouldBe Seq()
  }

  it should "add invitation to the list" in {
    val storage = new Storage()
    val expected = Seq(Invitation("1", "ala", "a@foo.com", NotConfirmed))
    storage.addInvitation("ala", "a@foo.com")
    storage.invitations() shouldBe expected
  }

  it should "not accept wrong id" in {
    val storage = new Storage()
    storage.addInvitation("ala", "a@foo.com")
    storage.confirm("a") shouldBe Left(InvitationNotFound)
    storage.decline("a") shouldBe Left(InvitationNotFound)
  }

  it should "confirm invitation" in {
    val storage = new Storage()
    val invitation = storage.addInvitation("ala", "a@foo.com")
    val expected = Invitation(invitation.id, "ala", "a@foo.com", Confirmed)
    storage.confirm(invitation.id) shouldBe Right(expected)
  }

  it should "decline invitation" in {
    val storage = new Storage()
    val invitation = storage.addInvitation("ala", "a@foo.com")
    val expected = Invitation(invitation.id, "ala", "a@foo.com", Declined)
    storage.decline(invitation.id) shouldBe Right(expected)
  }

  it should "remember invitation status" in {
    val storage = new Storage()
    val expected = Seq(
      Invitation("1", "ala", "a@foo.com", Confirmed),
      Invitation("2", "ala", "b@foo.com", Declined)
    )
    storage.addInvitation("ala", "a@foo.com")
    storage.addInvitation("ala", "b@foo.com")
    storage.confirm("1")
    storage.decline("2")

    storage.invitations().sortBy(_.id) shouldBe expected
  }

}

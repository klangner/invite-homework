package pl.klangner.invite

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import pl.klangner.invite.Models._
import pl.klangner.invite.ModelsJsonProtocol._
import spray.json._


/**
  * Created by Krzysztof Langner on 2018-07-12.
  */
class RestApiTest extends WordSpec with Matchers with ScalatestRouteTest with SprayJsonSupport {

  private def mainRoute() = {
    val storage = new Storage()
    val inviteApi = new InviteApi(storage)
    Main.route(inviteApi)
  }

  private def addInvitationRequest(params: InvitationParams): HttpRequest = {
    HttpRequest(
      HttpMethods.POST,
      uri = "/invitation",
      entity = HttpEntity(MediaTypes.`application/json`, params.toJson.compactPrint))
  }

  "The service" should {

    "list invitation" in {
      Get("/invitation") ~> mainRoute() ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[Seq[Invitation]] shouldEqual Seq[Invitation]()
      }
    }

    "Add new invitation" in {
      val invitationParams = InvitationParams("John Smith", "john@smith.mx")
      addInvitationRequest(invitationParams) ~> mainRoute() ~> check {
        status shouldEqual StatusCodes.Created
        responseAs[Invitation] shouldEqual Invitation("1", "John Smith", "john@smith.mx", NotConfirmed)
      }
    }

    "show new invitation on the list" in {
      val invitationParams = InvitationParams("John Smith", "john@smith.mx")
      val expected = Seq(Invitation("1", "John Smith", "john@smith.mx", NotConfirmed))
      val route = mainRoute()

      // Add invitation
      addInvitationRequest(invitationParams) ~> route ~> check {
        status shouldEqual StatusCodes.Created
      }
      // List invitations
      Get("/invitation") ~> route ~> check {
        responseAs[Seq[Invitation]] shouldEqual expected
      }
    }

    "Confirm invitation" in {
      val invitationParams = InvitationParams("John Smith", "john@smith.mx")
      val route = mainRoute()

      // Add invitation
      addInvitationRequest(invitationParams) ~> route ~> check {
        status shouldEqual StatusCodes.Created
      }
      Post("/invitation/1/confirm") ~> route ~> check {
        status shouldEqual StatusCodes.OK
      }
    }

    "Decline invitation" in {
      val invitationParams = InvitationParams("John Smith", "john@smith.mx")
      val route = mainRoute()

      // Add invitation
      addInvitationRequest(invitationParams) ~> route ~> check {
        status shouldEqual StatusCodes.Created
      }
      Post("/invitation/1/decline") ~> route ~> check {
        status shouldEqual StatusCodes.OK
      }
    }

    "confirm and decline invitation" in {
      val invitationParams1 = InvitationParams("ala", "a@foo.com")
      val invitationParams2 = InvitationParams("ala", "b@foo.com")
      val invitationParams3 = InvitationParams("ala", "c@foo.com")
      val expected = Seq(
        Invitation("1", "ala", "a@foo.com", NotConfirmed),
        Invitation("2", "ala", "b@foo.com", Confirmed),
        Invitation("3", "ala", "c@foo.com", Declined)
      )
      val route = mainRoute()

      // Add invitation
      addInvitationRequest(invitationParams1) ~> route ~> check {
        status shouldEqual StatusCodes.Created
      }
      addInvitationRequest(invitationParams2) ~> route ~> check {
        status shouldEqual StatusCodes.Created
      }
      addInvitationRequest(invitationParams3) ~> route ~> check {
        status shouldEqual StatusCodes.Created
      }
      // Confirm second
      Post("/invitation/2/confirm") ~> route ~> check {
        status shouldEqual StatusCodes.OK
      }
      // Decline third
      Post("/invitation/3/decline") ~> route ~> check {
        status shouldEqual StatusCodes.OK
      }
      // List invitations
      Get("/invitation") ~> route ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[Seq[Invitation]].sortBy(_.id) shouldEqual expected
      }
    }

    "Not confirm non-existing invitation" in {
      Post("/invitation/1/confirm") ~> mainRoute() ~> check {
        status shouldEqual StatusCodes.NotFound
      }
    }

    "Not decline non-existing invitation" in {
      Post("/invitation/1/decline") ~> mainRoute() ~> check {
        status shouldEqual StatusCodes.NotFound
      }
    }
  }
}

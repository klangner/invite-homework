package pl.klangner.invite

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import pl.klangner.invite.Main.storage
import pl.klangner.invite.Models.{Invitation, InvitationParams, NotConfirmed}
import pl.klangner.invite.ModelsJsonProtocol._
import spray.json._


/**
  * Created by Krzysztof Langner on 2018-07-12.
  */
class RestApiTest extends WordSpec with Matchers with ScalatestRouteTest with SprayJsonSupport {

    private def mainRoute() = {
      val inviteApi = new InviteApi(storage)
      Main.route(inviteApi)
    }

    "The service" should {

      "list invitation" in {
        Get("/invitation") ~> mainRoute() ~> check {
          status shouldEqual StatusCodes.OK
          responseAs[Seq[Invitation]] shouldEqual Seq[Invitation]()
        }
      }

      "Add new invitation" in {
        val jsonRequest = InvitationParams("John Smith", "john@smith.mx").toJson.compactPrint
        val postRequest = HttpRequest(
          HttpMethods.POST,
          uri = "/invitation",
          entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
        postRequest ~> mainRoute() ~> check {
          status shouldEqual StatusCodes.Created

          responseAs[Invitation] shouldEqual Invitation("1", "John Smith", "john@smith.mx", NotConfirmed)
        }
      }

      "Confirm invitation" in {
        Post("/invitation/1/confirm") ~> mainRoute() ~> check {
          status shouldEqual StatusCodes.OK
        }
      }

      "Decline invitation" in {
        Post("/invitation/1/decline") ~> mainRoute() ~> check {
          status shouldEqual StatusCodes.OK
        }
      }
    }
  }

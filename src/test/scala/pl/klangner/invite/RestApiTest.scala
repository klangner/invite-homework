package pl.klangner.invite

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by Krzysztof Langner on 2018-07-12.
  */
class RestApiTest extends WordSpec with Matchers with ScalatestRouteTest {

    "The service" should {

      "list invitation" in {
        // tests:
        Get("/invitation") ~> Main.route ~> check {
          status shouldEqual StatusCodes.OK
          responseAs[String] shouldEqual "[]"
        }
      }

      "Add new invitation" in {
        // tests:
        Post("/invitation") ~> Main.route ~> check {
          status shouldEqual StatusCodes.Created
          responseAs[String] shouldEqual """{ "id":"1", "invitee": "John Smith", "email": "john@smith.mx" }"""
        }
      }

      "Confirm invitation" in {
        // tests:
        Post("/invitation/1/confirm") ~> Main.route ~> check {
          status shouldEqual StatusCodes.OK
        }
      }

      "Decline invitation" in {
        // tests:
        Post("/invitation/1/decline") ~> Main.route ~> check {
          status shouldEqual StatusCodes.OK
        }
      }
    }
  }

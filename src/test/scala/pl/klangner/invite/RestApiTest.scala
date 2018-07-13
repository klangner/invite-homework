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
        val storage = new Storage()
        Get("/invitation") ~> Main.route(storage) ~> check {
          status shouldEqual StatusCodes.OK
          responseAs[String] shouldEqual "[]"
        }
      }

      "Add new invitation" in {
        val storage = new Storage()
        Post("/invitation") ~> Main.route(storage) ~> check {
          status shouldEqual StatusCodes.Created
          responseAs[String] shouldEqual """{ "id":"1", "invitee": "John Smith", "email": "john@smith.mx" }"""
        }
      }

      "Confirm invitation" in {
        val storage = new Storage()
        Post("/invitation/1/confirm") ~> Main.route(storage) ~> check {
          status shouldEqual StatusCodes.OK
        }
      }

      "Decline invitation" in {
        val storage = new Storage()
        Post("/invitation/1/decline") ~> Main.route(storage) ~> check {
          status shouldEqual StatusCodes.OK
        }
      }
    }
  }

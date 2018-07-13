package pl.klangner.invite

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory
import pl.klangner.invite.Models.InvitationParams
import pl.klangner.invite.ModelsJsonProtocol._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object Main extends SprayJsonSupport {

  private val Log = LoggerFactory.getLogger(Main.getClass.getName)

  implicit val system: ActorSystem = ActorSystem("invitation-app")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val storage = new Storage()
  val inviteApi = new InviteApi(storage)

  /** Main route of the application */
  def route(inviteApi: InviteApi): Route = {

    path("invitation" / Segment / "confirm") { id =>
      post {
        inviteApi.confirm(id)
      }
    } ~ path("invitation" / Segment / "decline") { id =>
      post {
        inviteApi.decline(id)
      }
    } ~ path("invitation") {
      get {
        inviteApi.listInvitations()
      }
    } ~ path("invitation") {
      post {
        entity(as[InvitationParams]) { params =>
          inviteApi.addInvitation(params)
        }
      }
    }
  }

    /** App main entry */
    def main(args: Array[String]) {
      Log.info("Server started")

      Await.result(Http().bindAndHandle(route(inviteApi), "0.0.0.0", 8080), Duration.Inf)
    }

  }
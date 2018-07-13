package pl.klangner.invite

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main {

  private val Log = LoggerFactory.getLogger(Main.getClass.getName)

  implicit val system: ActorSystem = ActorSystem("invitation-app")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  /** Main route of the application */
  val route: Route = {

    path("invitation") {
      get {
        InviteApi.listInvitations()
      }
    } ~ path("invitation") {
      post {
        InviteApi.addInvitation()
      }
    } ~ path("invitation" / Segment / "confirm") { id =>
      post {
        InviteApi.confirm(id)
      }
    } ~ path("invitation" / Segment / "decline") { id =>
      post {
        InviteApi.decline(id)
      }
    }
  }

  /** App main entry */
  def main(args: Array[String]) {
    Log.info("Server started")

    Await.result(Http().bindAndHandle(route, "0.0.0.0", 8080), Duration.Inf)
  }

}
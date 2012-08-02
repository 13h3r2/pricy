package playground

import akka.pattern._
import com.typesafe.config.ConfigFactory
import akka.actor.{Props, ActorSystem}
import ru.mtg.actors.{HTMLParser, HTTPFetcher, TotalCountRequest, TotalCountActor}
import akka.routing.FromConfig
import akka.util.Timeout
import akka.util.duration._
import akka.dispatch.Await

object Sandbox3 extends App {

  implicit val timeout = Timeout(1.minute)

  val config = ConfigFactory.load
  val system = ActorSystem("TestSystem", config.getConfig("pricy"))
  system.actorOf(Props[HTTPFetcher].withRouter(FromConfig), "fetcher")
  system.actorOf(Props[HTMLParser].withRouter(FromConfig), "parser")

  val result = system.actorOf(Props[TotalCountActor]) ask new TotalCountRequest()
  println(Await.result(result, (1.minute)))
  System.exit(0)
}

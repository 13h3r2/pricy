package playground

import com.typesafe.config.ConfigFactory
import akka.actor.{Props, ActorSystem}
import ru.mtg.actors.{HTMLParser, HTTPFetcher, StatisticUpdateMessage, StatisticUpdater}
import akka.routing.FromConfig

object Sandbox3 extends App {
  val config = ConfigFactory.load
  val system = ActorSystem("TestSystem", config.getConfig("pricy"))
  system.actorOf(Props[HTTPFetcher].withRouter(FromConfig), "fetcher")
  system.actorOf(Props[HTMLParser].withRouter(FromConfig), "parser")
  system.actorOf(Props[StatisticUpdater]) ! new StatisticUpdateMessage(10)
}

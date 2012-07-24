package ru.mtg

import akka.actor._
import akka.pattern._
import akka.util.Timeout
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import akka.dispatch.Future


object Sandbox extends App {
  val config = ConfigFactory.load
  val system = ActorSystem("TestSystem", config.getConfig("test"))
  implicit val timeout = Timeout(5000)
  val actor: ActorRef = system.actorOf(Props[HTTPRequestActor].withRouter(FromConfig()), "tester")
  (1 until 50)
    .map(x => { (actor ? ("http://sales.starcitygames.com/carddisplay.php?product=411" + x)).asInstanceOf[Future[String]]})
    .foreach(future => {future.onComplete(x=>println("got future!"))})

}

package ru.mtg

import akka.actor._
import akka.pattern._
import akka.util.{Duration, Timeout}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import akka.dispatch.{Await, Futures, Future}


object Sandbox extends App {
  val config = ConfigFactory.load
  val system = ActorSystem("TestSystem", config.getConfig("test"))
  implicit val timeout = Timeout(10000000)
  val actor: ActorRef = system.actorOf(Props[HTTPRequestActor].withRouter(FromConfig()), "http-router")
  val futures = (1 until 10).map(x => { (actor ? ("http://sales.starcitygames.com/carddisplay.php?product=411" + x)).asInstanceOf[Future[HTTPRequestResult]]})
  implicit val d = system.dispatcher
  Await.result(Future.sequence(futures), Duration.Inf).foreach(result =>
    println(result.url)
  )
//    .foreach(future => {future.onComplete(x=>println("got future!"))})

}

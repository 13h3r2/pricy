package ru.mtg

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask
import akka.dispatch.Future
import akka.routing.FromConfig


object Sandbox extends App {
  print("start")
  val as = ActorSystem.create
  implicit val timeout = Timeout(5000)
  val actor: ActorRef = as.actorOf(Props[HTTPRequestActor].withRouter(FromConfig("http-router")), name = "router")
  (1 until 10).map(x => { (actor ? ("http://sales.starcitygames.com/carddisplay.php?product=4117" + x)).asInstanceOf[Future[String]]})
    .foreach(future => {future.onComplete(x=>println("got future!"))})

}

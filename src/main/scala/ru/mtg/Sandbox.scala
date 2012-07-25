package ru.mtg

import akka.actor._
import akka.util.{Duration, Timeout}
import com.typesafe.config.ConfigFactory
import akka.dispatch.{Await, Future}
import akka.routing.RoundRobinRouter


object Sandbox extends App {
  val config = ConfigFactory.load
  val system = ActorSystem("TestSystem", config.getConfig("test"))
  implicit val timeout = Timeout(10000000)
  val actors = (1 to 3).map(i => TypedActor(system).getActorRefFor(TypedActor(system).typedActorOf(TypedProps[HTTPClientImpl].withTimeout(timeout))))
  val actor: HTTPClient = TypedActor(system).typedActorOf(
    TypedProps[HTTPClientImpl].withTimeout(timeout),
    system.actorOf(new Props().withRouter(RoundRobinRouter(actors.toIterable)))
  )
  val futures = (1 until 10).map(x => {
    val url = "http://sales.starcitygames.com/carddisplay.php?product=411" + x
    actor.fetch(url)
  })
  implicit val d = system.dispatcher
  Await.result(Future.sequence(futures), Duration.Inf)
    .foreach(result => println(result.url))
}

package ru.mtg

import akka.actor._
import akka.pattern._
import akka.util.{Duration, Timeout}
import com.typesafe.config.ConfigFactory
import akka.dispatch.{Await, Future}
import akka.routing.RoundRobinRouter


object Sandbox extends App {
  val config = ConfigFactory.load
  val system = ActorSystem("TestSystem", config.getConfig("test"))
  implicit val timeout = Timeout(10000000)
  //  val actor: ActorRef = system.actorOf(Props[HTTPRequestActor].withRouter(FromConfig()), "http-router")
  //  val futures = (1 until 10).map(x => { (actor ? ("http://sales.starcitygames.com/carddisplay.php?product=411" + x)).asInstanceOf[Future[HTTPRequestResult]]})
  val actors = (1 to 3)
    .map(i => {
      println("create" + i)
      val a : HTTPClient = TypedActor(system).typedActorOf(TypedProps[HTTPClientImpl].withTimeout(timeout), "http-router222" + i)
      TypedActor(system).getActorRefFor(a)
    })

  val router: RoundRobinRouter = RoundRobinRouter(actors.toIterable)

  val actor : HTTPClient = TypedActor(system).typedActorOf(
    TypedProps[HTTPClientImpl].withTimeout(timeout),
    system.actorOf(new Props().withRouter(router), "aaaa")
  )
//  val actor : HTTPClient = TypedActor(system).typedActorOf(TypedProps[HTTPClientImpl].withTimeout(timeout), "http-router111")
  val futures = (1 until 10).map(x => {
    val url = "http://sales.starcitygames.com/carddisplay.php?product=411" + x
    actor.fetch(url)
  })
  implicit val d = system.dispatcher
  Await.result(Future.sequence(futures), Duration.Inf).foreach(result =>
    println(result.url)
  )
}

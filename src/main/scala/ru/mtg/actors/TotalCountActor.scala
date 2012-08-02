package ru.mtg.actors

import akka.actor.{ActorLogging, Actor}
import akka.pattern._
import akka.util.duration._
import org.apache.commons.lang.StringUtils
import akka.util.Timeout
import akka.dispatch.Await
import akka.event.LoggingReceive

case class TotalCountRequest()

class TotalCountActor extends Actor with ActorLogging {
  protected def receive = LoggingReceive {
    case TotalCountRequest() => {
      implicit val timeout = Timeout(1.minute)
      val fetcher = context.actorFor("/user/fetcher")
      val html = Await.result(fetcher.ask(SSGLink()).mapTo[String], timeout.duration)
      val count = StringUtils.substringAfterLast(
        StringUtils.substringBefore(html, "items found."),
        ">")
      sender ! count
    }
  }
}

package ru.mtg.actors

import akka.actor.{SerializedActorRef, Actor}
import akka.pattern._
import akka.dispatch.{Await, Promise}
import xml.Node
import akka.util.duration._
import org.apache.commons.lang.StringUtils
import akka.util.Timeout

case class StatisticUpdateMessage(pages:Int)

class StatisticUpdater extends Actor {
  protected def receive = {
    case StatisticUpdateMessage(pages) => {
      //determine pages count
      val fetcher = context.actorFor("/user/fetcher")
      val parser = context.actorFor("/user/parser")


      val page = Await.result( for {
        page <- fetcher.ask(SSGLink())(Timeout(1.minute)).mapTo[String]
        parsedPage <- parser.ask(page)(Timeout(10.seconds)).mapTo[Node]
      } yield parsedPage
      , 1.minute).text

      val count = StringUtils.substringAfterLast(
        StringUtils.substringBefore(page, "items found."),
        ">")
      print(count)

      //fetch pages

      //parse it

      //save changes
    }
  }
}

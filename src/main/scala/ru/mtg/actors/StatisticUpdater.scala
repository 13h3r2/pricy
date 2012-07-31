package ru.mtg.actors

import akka.actor.{SerializedActorRef, Actor}
import akka.pattern._

case class StatisticUpdateMessage(pages:Int)

class StatisticUpdater extends Actor {
  protected def receive = {
    case StatisticUpdateMessage(pages) => {
      //determine pages count
      val fetcher = context.actorFor("fetcher")


      //fetch pages

      //parse it

      //save changes
    }
  }
}

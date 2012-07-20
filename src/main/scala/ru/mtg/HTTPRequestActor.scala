package ru.mtg

import akka.actor.Actor
import org.apache.commons.io.IOUtils
import java.net.URI
import akka.event.Logging

class HTTPRequestActor extends Actor {
  val log = Logging(context.system, this)

  override def preStart() = {
    log.debug("Starting")
  }

  protected def receive = {
    case url: String => {
      log.debug("start" + url)
      val result = IOUtils.toString(new URI(url))
      log.debug("finished" + url)
      sender ! result
    }
  }
}

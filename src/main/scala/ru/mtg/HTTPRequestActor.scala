package ru.mtg

import akka.actor.{ActorLogging, Actor}
import org.apache.commons.io.IOUtils
import java.net.URI
import akka.event.Logging

case class HTTPRequestResult(url : String, text : String)

class HTTPRequestActor extends Actor with ActorLogging {

  override def preStart() = {
    log.debug("Starting")
  }

  protected def receive = {
    case url: String => {
      log.debug("start" + url)
      val result = IOUtils.toString(new URI(url))
      log.debug("finished" + url)
      sender ! new HTTPRequestResult(url, result)
    }
  }
}

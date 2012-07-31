package playground

import akka.actor.{ActorLogging, Actor}
import java.net.URI
import org.apache.commons.io.IOUtils
import akka.dispatch.{Promise, Future}
import akka.actor.TypedActor.PreStart
import akka.event.LogSource

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

case class HTTPRequestResult(url: String, text: String)

trait HTTPClient {
  def fetch(url: String) : Future[HTTPRequestResult]
}


class HTTPClientImpl(val name: String) extends HTTPClient with PreStart with LogSource[HTTPClientImpl] {
  import akka.actor.TypedActor.dispatcher

  def this() = this("default")

  def fetch(url: String) = Promise.successful( {
    println("fetch")
    new HTTPRequestResult(url, IOUtils.toString(new URI(url)))
  })

  def preStart() {
    println("Starting HTTP client")
  }

  def genString(t: HTTPClientImpl) = this.toString
}


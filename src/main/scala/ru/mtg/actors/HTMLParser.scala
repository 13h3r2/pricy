package ru.mtg.actors

import nu.validator.htmlparser.sax.HtmlParser
import nu.validator.htmlparser.common.XmlViolationPolicy
import akka.actor.{ActorLogging, Actor}
import java.io.StringReader
import xml.parsing.NoBindingFactoryAdapter
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParser
import xml.Node

class HTMLParser extends Actor with ActorLogging {
  protected def receive = {
    case text: String => {
      val result = HTMLParser2.loadXML(text)
      sender ! result
    }
  }
}

object HTMLParser2 extends NoBindingFactoryAdapter {

  override def loadXML(source: InputSource, _p: SAXParser) = loadXML(source)

  def loadXML(source: String): Node = {
    loadXML(new InputSource(new StringReader(source)))
  }

  def loadXML(source: InputSource): Node = {
    val reader = new HtmlParser
    reader.setXmlPolicy(XmlViolationPolicy.ALLOW)
    reader.setContentHandler(this)
    reader.parse(source)
    rootElem
  }
}

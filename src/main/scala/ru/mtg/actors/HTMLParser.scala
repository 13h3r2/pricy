package ru.mtg.actors

import xml._
import parsing.NoBindingFactoryAdapter
import nu.validator.htmlparser.sax.HtmlParser
import nu.validator.htmlparser.common.XmlViolationPolicy
import akka.actor.Actor
import java.io.StringReader

class HTMLParser extends Actor {
  protected def receive = {
    case text : String => HTMLParser.loadXML(text)
  }
}

object HTMLParser extends NoBindingFactoryAdapter {

  override def loadXML(source: InputSource, _p: SAXParser) = loadXML(source)

  def loadXML(source: String) = {
    loadXML(new InputSource(new StringReader(source)))
  }

  def loadXML(source: InputSource) = {
    val reader = new HtmlParser
    reader.setXmlPolicy(XmlViolationPolicy.ALLOW)
    reader.setContentHandler(this)
    reader.parse(source)
    rootElem
  }
}

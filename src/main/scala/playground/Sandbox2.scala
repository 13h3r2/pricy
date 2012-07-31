package playground

import akka.actor._
import akka.util.{Duration, Timeout}
import com.typesafe.config.ConfigFactory
import akka.dispatch.{Await, Future}
import akka.routing.RoundRobinRouter


object Sandbox2 extends App {
  def isPrime(n: Int): Boolean =
    (2 until n) forall {
      n % _ != 0
    }

  val start = System.currentTimeMillis()
  (1 to 1000000).forall({
    isPrime(_)
  })
  val end = System.currentTimeMillis()
  println(end - start)
}

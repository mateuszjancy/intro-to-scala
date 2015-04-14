package com.intro.to.scala.w3.example

import akka.actor._

object App {
  def main(args: Array[String]) {
    val system = ActorSystem("PingPongSystem")
    val pong = system.actorOf(Props(Pong(None)), "PongActor")
    val ping = system.actorOf(Props(Ping(Some("Super Ping"), pong)), "PingActor")

    ping ! Play(10)
  }
}

case class Play(num: Int)

case class PingMsg(counter: Int)

case class PongMsg(counter: Int)

object Ping {
  def apply(msg: Option[String], pong: ActorRef) = new Ping(msg, pong)
}

trait Msg {
  this: ActorLogging =>
  def buildMsg(msg: String, i: Int): Unit = {
    log.info(msg + ": " + i)
  }
}

class Ping(msg: Option[String], pong: ActorRef) extends Actor with ActorLogging with Msg {
  val pingMsg = msg.getOrElse("Ping")

  def receive = {
    case Play(num) if num > 0 => {
      buildMsg(pingMsg, num)
      pong ! PingMsg(num - 1)
    }
    case PongMsg(counter) if counter > 0 => {
      buildMsg(pingMsg, counter)
      pong ! PingMsg(counter - 1)
    }
    case _ => log.info("End")
  }


}

object Pong {
  def apply(msg: Option[String]) = new Pong(msg)
}

class Pong(msg: Option[String]) extends Actor with ActorLogging with Msg {
  val pongMsg = msg.getOrElse("Pong")

  def receive = {
    case PingMsg(counter) if counter > 0 => {
      buildMsg(pongMsg, counter)

      sender ! PongMsg(counter - 1)
    }
    case _ => log.info("End")
  }
}



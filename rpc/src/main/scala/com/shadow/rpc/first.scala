package com.shadow.rpc.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}


class HelloActor extends Actor {
  def receive = {
    case "hello" => println("您好！")
    case _ => println("您是?")
  }
}

object HelloWorldApp {
  def main(args: Array[String]) {
    val system: ActorSystem = ActorSystem("HelloSystem")
    // 缺省的Actor构造函数
    val helloActor: ActorRef = system.actorOf(Props[HelloActor], name = "helloactor")
    helloActor ! "hello"
    helloActor ! "喂"
  }
}



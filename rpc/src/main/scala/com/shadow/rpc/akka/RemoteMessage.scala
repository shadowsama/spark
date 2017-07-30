package com.shadow.rpc.akka


trait RemoteMessage extends Serializable

//Worker -> Master
case class RegisterWorker(id: String, memory: Int, cores: Int) extends RemoteMessage

case class Heartbeat(id: String) extends RemoteMessage


//Master -> Worker
case class RegisteredWorker(masterUrl: String) extends RemoteMessage

//Worker -> self
case object SendHeartbeat

// Master -> self
case object CheckTimeOutWorker


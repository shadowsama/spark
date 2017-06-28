package org.shadow.scala.thread

import java.util.concurrent.{Callable, Executors, TimeUnit}

object ThreadPool02 {

  def main(args: Array[String]) {
    val threadPool = Executors.newFixedThreadPool(10)
    val synTask = new Callable[Int] {
      override def call(): Int = {
        var i = 0
        println("start compute")
        Thread.sleep(10000)
        i = 1000
        i
      }
    }
    val future = threadPool.submit(synTask)
    val result = future.get()
    println(result)

  }
}

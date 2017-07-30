package org.shadow.scala.thread


object Bootstrap {

  def main(args: Array[String]) {
    val eventLoop = new TaskProcessEventLoop("task-event-loop")
    eventLoop.start()
    for (i <- 1 to 10) {
      eventLoop.post(TaskSubmitted(s"task-$i"))//把任务放到队列中
    }
    Thread.sleep(10000)
  }
}

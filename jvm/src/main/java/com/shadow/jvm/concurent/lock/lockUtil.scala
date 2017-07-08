package com.shadow.jvm.concurent.lock

import java.util.concurrent.locks.Lock

/**
  * Created by shadow on 2017/7/6 0006.
  */
object lockUtil {


  /**
    * Execute the given function inside the lock
    */
  def inLock[T](lock: Lock,fun: => T):T= {
    lock.lock()
    try {
      fun
    } finally {
      lock.unlock()
    }
  }

}

class lockUtil{}

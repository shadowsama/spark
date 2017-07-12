package org.shadow.scala.implic


/** 
 * Class: 
 * Author: wanghf
 * Date: 2017/7/12 0012  22:43
 * Descrption: 隐式值得使用
 */
object MyPredef{
  implicit  val a ="spark 2.2.0"
}
object implicitValue {


  def hello(implicit name:String ="spark"): Unit ={

    println(s"hello $name")
  }


  def main(args: Array[String]): Unit = {

    // 隐士值得调用
    hello //hello spark

    //implicit  val a ="spark 2.2.0"

    import  MyPredef._

    // 从上下文找隐式值
    hello // hello spark 2.2.0


  }



}



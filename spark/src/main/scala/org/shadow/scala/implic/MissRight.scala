package org.shadow.scala.implic


class MissRight[T] {

  // 相当于视图界定 传一个函数  <%
  def choose(first: T, second: T)(implicit ord : T => Ordered[T]): T = {
    if(first > second) first else second
  }
  // 相当于上下文界定 传一个值 :
  def select(first: T, second: T)(implicit ord : Ordering[T]): T ={
    if(ord.gt(first, second)) first else second
  }

  def random(first: T, second: T)(implicit ord : Ordering[T]): T ={
    import Ordered.orderingToOrdered
    if(first > second) first else second
  }

}

object MissRight {
  def main(args: Array[String]) {
    val mr = new MissRight[Girl]
    val g1 = new Girl("hatanao", 98, 28)
    val g2 = new Girl("sora", 95, 33)

    //val g = mr.choose(g1, g2)
    //val g = mr.select(g1, g2)
   // println(g.name)
  }
}

package org.shadow.scala.implic


/**
 * Class:
 * Author: wanghf
 * Date: 2017/7/13 0013  0:10
 * Descrption:
 */
class Chooser[T <% Ordered[T]] {

  def choose(a: T, b: T): T = {

    // 需要定义比较规则 > 为ordered的方法
    if (a > b) {
      a
    } else {
      b
    }
  }

}


class person(val name:String, val age:Int)



object Chooser{
  def main(args: Array[String]): Unit = {

//    implicit def person2OrderedDesc(p : person) = new Ordered[person] {
//      override def compare(that: person): Int = {
//        p.age - that.age
//      }
//    }

    implicit val person2OrderedDesc = (p : person) => new Ordered[person] {
      override def compare(that: person): Int = {
        p.age - that.age
      }
    }

    val a = new person("a",1)

    val b = new person("b",2)

    // Error:(30, 16) No implicit view available from org.shadow.scala.implic.person => Ordered[org.shadow.scala.implic.person].

    val unit = new Chooser[person]
    val person = unit.choose(a,b)

    println(person.age)




  }
}

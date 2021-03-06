package org.shadow.scala.implic

import java.io.File

import scala.io.Source


class RichFile(file: File) {
  def read() = Source.fromFile(file).mkString
}

object Context {
  implicit def file2RichFile(file: File) : RichFile = {
    new RichFile(file)
  }
}

object RichFile {
  def main(args: Array[String]) {
    //val contents = new RichFile(new File("c://words.txt")).read()
    import Context._
    val contents = new File("c://words.txt").read()
    println(contents)
  }
}



package org.dbpedia.extraction.dump.util

import scala.collection.mutable.ArrayBuffer

trait A
trait B extends B

/*
multi-line
comment
 */
class someAnotation extends scala.annotation.Annotation
@someAnotation
object ExampleScala extends App {
  var floatValue = 5.21 // comment
  val sum = 5.21 + 3
  sum match {
    case _ > 0 => print("more than zero")
    case _ => print(s"$sum less or equal to zero")
  }
  /*
multi-line
comment

 */
  def function[T <: A](str: T): Array[T] = {
    val array: Array[T] = Array(new B {})
    array

  }

  sum.todo

  val fal +++++
  println(sum)
}


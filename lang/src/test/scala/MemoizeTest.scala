/*
 * Copyright (C) 2017-present, Chenai Nakam(chenai.nakam@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import hobby.chenai.nakam.tool.cache._

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 22/07/2017
  */
object MemoizeTest extends LruMemSyncGet[String, Int] {
  override protected def maxCacheSize = 4

  override protected val delegate = new Delegate[String, Int] {
    var map: Map[String, Int] = Map("a" -> 0, "b" -> 1, "c" -> 2, "d" -> 3, "f" -> 5)

    override def load(key: String) = {
      print("load:" + key + " -> ")
      map.get(key)
    }

    override def update(key: String, value: Option[Int]) = {
      println("update:" + key + " -> " + value)
      map += key -> value.get
      println(map)
      true
    }
  }

  def main(args: Array[String]): Unit = {
    trait A {
      val i = 1

      def get = i

      def x(): Unit
    }

    trait B extends A {
      override val i = 5

      override def get = super.get + 1
    }

    trait Ax extends A {
      override val i = 100

      override def get = super.get * 2

      override def x() = ???
    }

    trait C extends B with Ax
    trait D extends Ax with B

    println(new C {}.get)
    println(new D {}.get)


    println(get("a"))
    println(get("d"))
    println(get("e"))
    println(get("f"))

    println(get("a"))
    println(get("d"))
    println(get("e"))
    println(get("f"))

    println("dirty----d, e")
    dirty("d")
    dirty("e")

    println(get("a"))
    println(get("d"))
    println(get("e"))
    println(get("f"))

    println("refresh----")
    refresh("b")

    println(get("b"))

    println(get("a"))
    println(get("d"))
    println(get("e"))
    println(get("f"))

    println("update----")
    update("e", Some(4))

    println(get("a"))
    println(get("d"))
    println(get("e"))
    println(get("f"))

    println("clear----")
    clear()

    println(get("a"))
    println(get("d"))
    println(get("e"))
    println(get("f"))
  }
}

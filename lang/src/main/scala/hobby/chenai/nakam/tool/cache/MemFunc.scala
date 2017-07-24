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

package hobby.chenai.nakam.tool.cache

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 25/07/2017
  */
protected[cache] trait MemFunc[K, V] {
  protected[cache] lazy val memory: MemStore[K, V] = new MemStore[K, V] {
    private var map = Map.empty[K, V]

    override def get(key: K): Option[V] = map.get(key)

    override def put(key: K, value: V): Boolean = {
      map += key -> value
      true
    }

    override def remove(key: K): Boolean = {
      map -= key
      true
    }

    override def clear(): Unit = map = Map.empty[K, V]
  }
}

protected trait MemStore[K, V] {
  def get(key: K): Option[V]

  def put(key: K, value: V): Boolean

  def remove(key: K): Boolean

  def clear(): Unit
}

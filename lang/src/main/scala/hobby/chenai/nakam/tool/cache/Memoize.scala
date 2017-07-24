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
  * @version 1.0, 22/07/2017
  */
trait Memoize[K, V] extends MemFunc[K, V] {
  /* 这个方案不行，很多类型 V 无法创建本 trait 实例。如：Int。因此改用了 nonValue4Keys 方案。
    protected trait DefValue {
      override final def equals(obj: Any) = obj match {
        case that: DefValue => that eq this
        case _ => false
      }
    }
    protected val defValue: V with DefValue
    */
  private var nonValue4Keys: Set[K] = Set.empty[K]

  protected val delegate: Delegate[K, V]

  def get(key: K): Option[V] = {
    memory.get(key) match {
      case value: Some[_] => value
      case _ => if (nonValue4Keys.contains(key)) None else refresh(key)
    }
  }

  def getOnly(key: K): Option[V] = memory.get(key)

  def refresh(key: K): Option[V] = put2Memory(key, delegateLoad(key))

  def dirty(key: K): Unit = {
    memory.remove(key)
    nonValue4Keys -= key
  }

  def update(key: K, value: Option[V]): Boolean = {
    if (delegateUpdate(key, value)) {
      put2Memory(key, value)
      true
    } else false
  }

  def clear(): Unit = {
    memory.clear()
    nonValue4Keys = Set.empty[K]
  }

  private def put2Memory(key: K, value: Option[V]): Option[V] = {
    value match {
      case Some(v) =>
        require(v != null)
        memValue(key, v)
      case _ => memNonValue(key)
    }
    value
  }

  private[cache] def delegateLoad(key: K) = delegate.load(key)

  private[cache] def delegateUpdate(key: K, value: Option[V]) = delegate.update(key, value)

  private[cache] def memValue(key: K, value: V): Unit = {
    memory.put(key, value)
    nonValue4Keys -= key
  }

  /** 数据库本来就没有对应值，则应该避免再次get的时候重新刷新。否则会更加耗性能，适得其反。 */
  private[cache] def memNonValue(key: K): Unit = {
    memory.remove(key)
    nonValue4Keys += key
  }
}

/**
  * 仅在第一次 `get(K)` 的时候，使用线程同步。等同于单例实现。
  */
trait MemSyncGet[K, V] extends Memoize[K, V] {
  override def get(key: K) = {
    memory.get(key) match {
      case some: Some[_] => some
      case _ => synchronized(super.get(key))
    }
  }
}

trait MemSync[K, V] extends MemSyncGet[K, V] {
  override def getOnly(key: K) = synchronized(super.getOnly(key))

  override def dirty(key: K) = synchronized(super.dirty(key))

  override def clear() = synchronized(super.clear())

  override private[cache] def delegateLoad(key: K) = synchronized(super.delegateLoad(key))

  override private[cache] def delegateUpdate(key: K, value: Option[V]) = synchronized(super.delegateUpdate(key, value))

  override private[cache] def memValue(key: K, value: V) = synchronized(super.memValue(key, value))

  override private[cache] def memNonValue(key: K) = synchronized(super.memNonValue(key))
}

trait Delegate[K, V] {
  def load(key: K): Option[V]

  def update(key: K, value: Option[V]): Boolean
}

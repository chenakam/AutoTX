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

package hobby.chenai.nakam.autotx.core.coin

import hobby.chenai.nakam.autotx.core.exch.AbsExchZone

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 25/05/2017
  */
abstract class AbsCoinZone {
  type COIN <: AbsCoin
  type WRAPPER <: AbsNumWrapper

  def make(count: Long): COIN

  def make(count: Double): WRAPPER

  abstract class AbsCoin(protected val count: Long, val unitName: String) extends Equals {
    val isMoney: Boolean

    def unit: COIN

    def toUnit: Double = this / unit // 小数点后超出最小单位的肯定都是0

    def toMinUnit: Long = count

    def +(that: COIN): COIN = make(this.count + that.count)

    def -(that: COIN): COIN = make(this.count - that.count)

    def *(x: Double): COIN = make((this.count.toDouble * x).toLong)

    def /(x: Double): COIN = make((this.count.toDouble / x).toLong)

    def /(that: COIN): Double = this.count.toDouble / that.count

    /*override def equals(any: scala.Any) = any match {
      // COIN 会被擦除，子类实现
      case that: COIN => that.canEqual(this) && that.count == this.count
      case _ => false
    }*/

    // 虽然COIN表示的是同一个对象（如BtcZone）下的路径依赖类型，即同一类对象（如BTC, CONG等），是可以进行比较的。
    // 但不能用COIN, 会被擦除，所以不能用isInstanceOf[COIN], 但也不能用AbsCoinZone#AbsCoin, 范围更广。
    // 子类实现。
    // override def canEqual(that: Any)

    override def hashCode() = 41 * (41 + count.hashCode()) + unitName.hashCode

    def to(that: AbsCoinZone#AbsCoin)(implicit exchange: AbsExchZone#AbsExchange):
    AbsCoinZone#AbsCoin = exchange.ex.applyOrElse((this, that), (x: (AbsCoinZone#AbsCoin, _)) => x._1)

    private def decimals(n: Double): Int = {
      if (n == 1) 0 else 1 + decimals(n / 10)
    }

    override def toString = if (this eq unit) unitName
    else toUnit formatted s"%.${decimals(unit.count)}f" + " " + unitName
  }

  abstract class AbsMoney(count: Long, unitName: String) extends AbsCoin(count: Long, unitName: String) {
    final lazy val isMoney = true
  }

  abstract class AbsToken(count: Long, unitName: String) extends AbsCoin(count: Long, unitName: String) {
    final lazy val isMoney = false
  }

  abstract class AbsNumWrapper(count: Double) {
    implicit def minUnit: COIN = make(count.toLong /*最小单位后的小数直接舍弃*/)
  }

}

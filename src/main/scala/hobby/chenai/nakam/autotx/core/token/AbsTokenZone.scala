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

package hobby.chenai.nakam.autotx.core.token

import hobby.chenai.nakam.autotx.core.Exchange

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 25/05/2017
  */
abstract class AbsTokenZone {
  type TOKEN <: AbsToken
  type WRAPPER <: AbsNumWrapper

  def make(count: Long): TOKEN

  def make(count: Double): WRAPPER

  protected val UNIT: TOKEN

  abstract class AbsToken(private val count: Long, val unitName: String) {
    def toUnit: Double = this / UNIT // 小数点后超出最小单位的肯定都是0

    def toMinUnit: Long = count

    def +(that: TOKEN): TOKEN = make(this.count + that.count)

    def -(that: TOKEN): TOKEN = make(this.count - that.count)

    def *(x: Double): TOKEN = make((this.count.toDouble * x).toLong)

    def /(x: Double): TOKEN = make((this.count.toDouble / x).toLong)

    def /(that: TOKEN): Double = this.count.toDouble / that.count

    def to(that: AbsTokenZone#AbsToken)(implicit exchange: Exchange): AbsTokenZone#AbsToken =
      exchange.currentRate.applyOrElse((this, that), (_: (AbsTokenZone#AbsToken, AbsTokenZone#AbsToken)) => this)

    private def decimals(n: Double): Int = {
      if (n == 1) 0 else 1 + decimals(n / 10)
    }

    override def toString = toUnit formatted ("%." + decimals(UNIT.count) + "f") + " " + unitName
  }

  abstract class AbsMoney(count: Long, unitName: String) extends AbsToken(count: Long, unitName: String) {
    lazy val isMoney = true
  }

  abstract class AbsNumWrapper(count: Double) {
    implicit def minUnit: TOKEN = make(count.toLong /*最小单位后的小数直接舍弃*/)
  }

}

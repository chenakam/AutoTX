/*
 * Copyright (C) 2019-present, Chenai Nakam(chenai.nakam@gmail.com)
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

package hobby.chenai.nakam.txdsl.coin

import hobby.chenai.nakam.txdsl.core.coin.AbsTokenGroup

import scala.language.implicitConversions
import scala.math.BigInt.int2bigInt

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 30/08/2020
  */
object YfiToken extends AbsTokenGroup {
  override type COIN = YearnFinance
  override type UNIT = COIN with Unt

  override def unitStd = YFI

  override def make(count: BigInt, unt: UNIT) = new YearnFinance(count) {
    override def unit = unt
  }

  abstract class YearnFinance private[YfiToken](count: BigInt) extends AbsCoin(count: BigInt) {
    override def equals(obj: Any) = obj match {
      case that: YearnFinance => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[YearnFinance]
  }

  lazy val YFI: UNIT = new YearnFinance(10.pow(8)) with Unt {
    override val name = "YFI"
  }

  class ImpDsl(count: BigDecimal) {
    @inline def YFI: COIN = YfiToken.YFI * count
  }

  @inline implicit def wrapYfiNum(count: Double): ImpDsl = new ImpDsl(count)
  @inline implicit def wrapYfiNum(count: BigDecimal): ImpDsl = new ImpDsl(count)
}

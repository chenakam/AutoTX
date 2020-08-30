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
object BchToken extends AbsTokenGroup {
  override type COIN = BitcoinCash
  override type UNIT = COIN with Unt

  override def unitStd = BCH

  override def make(count: BigInt, unt: UNIT) = new BitcoinCash(count) {
    override def unit = unt
  }

  abstract class BitcoinCash private[BchToken](count: BigInt) extends AbsCoin(count: BigInt) {
    override def equals(obj: Any) = obj match {
      case that: BitcoinCash => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[BitcoinCash]
  }

  lazy val BCH: UNIT = new BitcoinCash(10.pow(8)) with Unt {
    override val name = "BCH"
  }

  class ImpDsl(count: BigDecimal) {
    @inline def BCH: COIN = BchToken.BCH * count
  }

  @inline implicit def wrapBchNum(count: Double): ImpDsl = new ImpDsl(count)
  @inline implicit def wrapBchNum(count: BigDecimal): ImpDsl = new ImpDsl(count)
}

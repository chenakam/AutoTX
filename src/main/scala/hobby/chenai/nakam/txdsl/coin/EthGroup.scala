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

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 30/05/2017
  */
object EthGroup extends AbsTokenGroup {
  override type COIN = Token
  override type UNIT = COIN with Unt

  override def unitStd = ETH

  override def make(count: Long, unt: UNIT) = new Token(count) {
    override def unit = unt
  }

  abstract class Token private[EthGroup](count: Long) extends AbsCoin(count: Long) {
    override def equals(obj: Any) = obj match {
      case that: Token => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[Token]
  }

  // 既是单位数据也是枚举
  lazy val GWei: UNIT = new Token(1) with Unt {
    override val name = "GWei"
  }
  // 以太的单位比较混乱，暂保留到GWei(1e9Wei, 1e-9Ether)。如果小数位数太多，则留给整数的位数就会减少。
  lazy val ETH: UNIT = new Token(1000000000) with Unt {
    override val name = "ETH"
    // override val decmlFmt: Int = super.decmlFmt - 1 // 前一版设置了7个0但只保留6位有效位，故有次设置。
  }

  class ImpDsl(count: Double) {
    implicit def GWei: COIN = EthGroup.GWei * count

    implicit def ETH: COIN = EthGroup.ETH * count
  }

  implicit def wrapEthNum(count: Double): ImpDsl = new ImpDsl(count)
}

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

import scala.language.{existentials, implicitConversions, postfixOps}

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 30/05/2017
  */
object EthGroup extends AbsTokenGroup {
  override type COIN = Token
  override type UNIT = COIN with Unt

  override val name = "ETH"
  lazy override val UNIT = ETH

  override def make(count: Long, unt: UNIT) = new Token(count) {
    override def unit = unt

    override def unitName = unt.unitName
  }

  abstract class Token private[EthGroup](count: Long) extends AbsToken(count: Long) {
    override protected def decimals: Int = super.decimals - 1

    override def equals(obj: Any) = obj match {
      case that: Token => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[Token]
  }

  // 以太的单位比较混乱，暂保留7位，输出时保留6位(decimals - 1)。如果小数位数太多，则留给整数的位数就会减少。
  lazy val ETH: UNIT = new Token(10000000) with Unt {
    override def unit = this
  }

  class ImpDsl(count: Double) {
    implicit def ETH: COIN = EthGroup.ETH * count
  }

  implicit def wrapEthNum(count: Double): ImpDsl = new ImpDsl(count)
}

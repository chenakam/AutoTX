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
  * @version 1.0, 30/05/2017
  */
object EthToken extends AbsTokenGroup {
  override type COIN = Ethereum
  override type UNIT = COIN with Unt

  override def unitStd = ETH

  override def make(count: BigInt, unt: UNIT) = new Ethereum(count) {
    override def unit = unt
  }

  abstract class Ethereum private[EthToken](count: BigInt) extends AbsCoin(count: BigInt) {
    override def equals(obj: Any) = obj match {
      case that: Ethereum => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[Ethereum]
  }

  lazy val Wei: UNIT = new Ethereum(1) with Unt {
    override val name = "Wei"
  }
  lazy val KWei: UNIT = new Ethereum(10.pow(3)) with Unt {
    override val name = "KWei"
  }
  lazy val MWei: UNIT = new Ethereum(10.pow(6)) with Unt {
    override val name = "MWei"
  }
  lazy val GWei: UNIT = new Ethereum(10.pow(9)) with Unt {
    override val name = "GWei"
  }
  lazy val Szabo: UNIT = new Ethereum(10.pow(12)) with Unt {
    override val name = "Szabo"
  }
  lazy val Finney: UNIT = new Ethereum(10.pow(15)) with Unt {
    override val name = "Finney"
  }
  lazy val ETH: UNIT = new Ethereum(10.pow(18)) with Unt {
    override val name = "ETH" // Ether
    // override val decmlFmt: Int = super.decmlFmt - 1 // 前一版设置了7个0但只保留6位有效位，故有次设置。
  }

  class ImpDsl(count: BigDecimal) {
    @inline def Wei: COIN = EthToken.Wei * count
    @inline def KWei: COIN = EthToken.KWei * count
    @inline def MWei: COIN = EthToken.MWei * count
    @inline def GWei: COIN = EthToken.GWei * count
    @inline def Szabo: COIN = EthToken.Szabo * count
    @inline def Finney: COIN = EthToken.Finney * count
    @inline def ETH: COIN = EthToken.ETH * count
  }

  @inline implicit def wrapEthNum(count: Double): ImpDsl = new ImpDsl(count)
  @inline implicit def wrapEthNum(count: BigDecimal): ImpDsl = new ImpDsl(count)
}

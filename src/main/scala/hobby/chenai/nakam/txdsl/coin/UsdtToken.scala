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
  * @version 1.0, 08/05/2019
  */
object UsdtToken extends AbsTokenGroup {
  override type COIN = USDT
  override type UNIT = COIN with Unt

  override def unitStd = USDT

  override def make(count: BigInt, unt: UNIT) = new USDT(count) {
    override def unit = unt
  }

  abstract class USDT private[UsdtToken] (count: BigInt) extends AbsCoin(count: BigInt) {
    override val isStable = true

    override def equals(obj: Any) = obj match {
      case that: USDT => that.canEqual(this) && that.count == this.count
      case _          => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[USDT]
  }

  private trait UST extends Unt {
    override val decmlFmtFfd = super.decmlFmtFfd - 3
  }

  lazy val USFen3: UNIT = new USDT(1) with Unt {
    override val name = "UsFen3"

    override def nameFmt = USDT.nameFmt

    override def decmlFmtAdj = ONE / USDT.count
    override val decmlFmtFfd = decimals(USDT.count)
  }

  lazy val USFen: UNIT = new USDT(1000) with Unt {
    override val name = "UsFen"
  }

  lazy val USDT: UNIT = new USDT(100000) with Unt {
    override val name = "USDT"
  }

  class DslImpl(count: BigDecimal) {
    @inline def UsFen3: COIN = UsdtToken.USFen3 * count
    @inline def UsFen: COIN  = UsdtToken.USFen * count
    @inline def USDT: COIN   = UsdtToken.USDT * count
  }

  class DslImplInt(count: Int) {
    @inline def UsFen3: COIN = UsdtToken.USFen3 * count
    @inline def UsFen: COIN  = UsdtToken.USFen * count
    @inline def USDT: COIN   = UsdtToken.USDT * count
  }

  @inline implicit def wrapUsdtNum(count: Int): DslImplInt     = new DslImplInt(count)
  @inline implicit def wrapUsdtNum(count: Double): DslImpl     = new DslImpl(count)
  @inline implicit def wrapUsdtNum(count: BigDecimal): DslImpl = new DslImpl(count)
}

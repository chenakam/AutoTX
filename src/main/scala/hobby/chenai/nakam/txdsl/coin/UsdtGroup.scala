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
object UsdtGroup extends AbsTokenGroup {
  override type COIN = USDT
  override type UNIT = COIN with Unt

  override def unitStd = USDT

  override def make(count: BigInt, unt: UNIT) = new USDT(count) {
    override def unit = unt
  }

  abstract class USDT private[UsdtGroup](count: BigInt) extends AbsCoin(count: BigInt) {
    override def equals(obj: Any) = obj match {
      case that: USDT => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[USDT]
  }

  private trait UST extends Unt {
    override val decmlFmt: Int = super.decmlFmt - 3
  }

  lazy val UsFen_3: UNIT = new USDT(1) with Unt {
    override val name = "UsFen-3"

    override def nameFmt = USDT.nameFmt

    override val decmlFmt: Int = decimals(USDT.count)
  }
  lazy val UsFen: UNIT = new USDT(1000) with UST {
    override val name = "UsFen"
  }
  lazy val USDT: UNIT = new USDT(100000) with UST {
    override val name = "USDT"
  }

  class ImpDsl(count: BigDecimal) {
    implicit def UsFen_3: COIN = UsdtGroup.UsFen_3 * count

    implicit def UsFen: COIN = UsdtGroup.UsFen * count

    implicit def USDT: COIN = UsdtGroup.USDT * count
  }

  implicit def wrapUsdtNum(count: Double): ImpDsl = new ImpDsl(count)
  implicit def wrapUsdtNum(count: BigDecimal): ImpDsl = new ImpDsl(count)
}

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
object CnyZone extends AbsCoinZone {
  override type COIN = RMB
  override type UNIT = COIN with Unt

  override val name = "CNY"
  lazy override val UNIT = CNY

  override def make(count: Long, unt: UNIT) = new RMB(
    count // if (unt == FEN_3) count else count / 1000 * 1000 // 可以这样重构FEN以上的精度
  ) {
    override def unit = unt

    override def unitName = unt.unitName
  }

  abstract class RMB private[CnyZone](count: Long) extends AbsCash(count: Long) {
    override def value = if (unit eq FEN_3) value(CNY) else super.value

    override protected def decimals: Int = if (unit eq FEN_3) decimals(CNY.count) else super.decimals - 3

    protected override def toString$ = if (unit eq FEN_3) format + " " + CNY.unitName else super.toString$

    override def equals(obj: Any) = obj match {
      case that: RMB => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[RMB]
  }

  // SC云储币精确到0.00001
  lazy val FEN_3: UNIT = new RMB(1) with Unt {
    override def unit = this

    override val unitName = "FEN-3"
  }
  // 更高精度, 不过decimals会去掉3位，toString时会格式化掉。
  lazy val FEN: UNIT = new RMB(1000) with Unt {
    override def unit = this

    override val unitName = "FEN"
  }
  lazy val JIAO: UNIT = new RMB(10000) with Unt {
    override def unit = this

    override val unitName = "JIAO"
  }
  lazy val CNY: UNIT = new RMB(100000) with Unt {
    override def unit = this
  }

  class ImpDsl(count: Double) {
    implicit def FEN_3: COIN = CnyZone.FEN_3 * count

    implicit def FEN: COIN = CnyZone.FEN * count

    implicit def JIAO: COIN = CnyZone.JIAO * count

    implicit def CNY: COIN = CnyZone.CNY * count
  }

  implicit def wrapCnyNum(count: Double): ImpDsl = new ImpDsl(count)
}

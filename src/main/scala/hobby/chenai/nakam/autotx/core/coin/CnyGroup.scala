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
object CnyGroup extends AbsCashGroup {
  override type COIN = RMB
  override type UNIT = COIN with Unt

  lazy override val unitStd = CNY

  override def make(count: Long, unt: UNIT) = new RMB(
    count // if (unt == FEN_3) count else count / 1000 * 1000 // 可以这样重构FEN以上的精度
  ) {
    override def unit = unt
  }

  abstract class RMB private[CnyGroup](count: Long) extends AbsCash(count: Long) {
    // TODO:
//    override val value = if (unit eq FEN_3) mod(CNY).value else super.value

    override def equals(obj: Any) = obj match {
      case that: RMB => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[RMB]
  }

  private trait CNU extends Unt {
    override val decmlFmt: Int = super.decmlFmt - 3
  }

  // SC云储币精确到0.00001
  lazy val FEN_3: UNIT = new RMB(1) with Unt {
    override val name = "FEN-3"

    override def nameFmt = CNY.nameFmt

    override val decmlFmt: Int = decimals(CNY.count)
  }
  // 更高精度, 不过decimals会去掉3位，toString时会格式化掉。
  lazy val FEN: UNIT = new RMB(1000) with CNU {
    override val name = "FEN"
  }
  lazy val JIAO: UNIT = new RMB(10000) with CNU {
    override val name = "JIAO"
  }
  lazy val CNY: UNIT = new RMB(100000) with CNU {
    override val name = "CNY"
  }

  class ImpDsl(count: Double) {
    implicit def FEN_3: COIN = CnyGroup.FEN_3 * count

    implicit def FEN: COIN = CnyGroup.FEN * count

    implicit def JIAO: COIN = CnyGroup.JIAO * count

    implicit def CNY: COIN = CnyGroup.CNY * count
  }

  implicit def wrapCnyNum(count: Double): ImpDsl = new ImpDsl(count)
}

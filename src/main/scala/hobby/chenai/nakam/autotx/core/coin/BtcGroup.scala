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
  * @version 1.0, 29/05/2017
  */
object BtcGroup extends AbsTokenGroup {
  override type COIN = Token
  override type UNIT = COIN with Unt

  override val name = "BTC"
  lazy override val UNIT = BTC

  override def make(count: Long, unt: UNIT) = new Token(count) {
    override def unit = unt

    override def unitName = unt.unitName
  }

  abstract class Token private[BtcGroup](count: Long) extends AbsToken(count: Long) {
    override def equals(obj: Any) = obj match {
      case that: Token => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[Token]
  }

  // 既是单位数据也是枚举
  lazy val CONG: UNIT = new Token(1) with Unt {
    override def unit = this

    override val unitName = "CONG"
  }
  lazy val BTC: UNIT = new Token(100000000) with Unt { // 一亿聪
    override def unit = this
  }

  class ImpDsl(count: Double) {
    implicit def CONG: COIN = BtcGroup.CONG * count

    implicit def BTC: COIN = BtcGroup.BTC * count
  }

  // 不可以写在父类里，否则对于多个不同的币种就不知道转换给谁了。
  implicit def wrapBtcNum(count: Double): ImpDsl = new ImpDsl(count)
}

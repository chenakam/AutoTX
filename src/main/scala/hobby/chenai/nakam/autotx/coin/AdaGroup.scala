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

package hobby.chenai.nakam.autotx.coin

import hobby.chenai.nakam.autotx.core.coin.AbsTokenGroup

import scala.language.implicitConversions

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 10/05/2019
  */
object AdaGroup extends AbsTokenGroup {
  override type COIN = Token
  override type UNIT = COIN with Unt

  override def unitStd = ADA

  override def make(count: Long, unt: UNIT) = new Token(count) {
    override def unit = unt
  }

  abstract class Token private[AdaGroup](count: Long) extends AbsCoin(count: Long) {
    override def equals(obj: Any) = obj match {
      case that: Token => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[Token]
  }

  lazy val ADA: UNIT = new Token(100000000) with Unt {
    override val name = "ADA"
  }

  class ImpDsl(count: Double) {
    implicit def ADA: COIN = AdaGroup.ADA * count
  }

  // 不可以写在父类里，否则对于多个不同的币种就不知道转换给谁了。
  implicit def wrapAdaNum(count: Double): ImpDsl = new ImpDsl(count)
}
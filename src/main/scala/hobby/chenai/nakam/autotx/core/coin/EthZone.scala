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

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 30/05/2017
  */
object EthZone extends AbsCoinZone {
  override type COIN = Token
  override type WRAPPER = Wrapper

  override def make(count: Long) = new Token(count, "ETH")

  class Token private[EthZone](count: Long, unitName: String) extends AbsToken(count: Long, unitName: String) {
    override def unit = ETH

    override def equals(obj: scala.Any) = obj match {
      case that: Token => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[Token]
  }

  // 既是单位数据也是枚举
  lazy val ETH = 1 ETH

  override def make(count: Double) = new Wrapper(count)

  class Wrapper(count: Double) extends AbsNumWrapper(count: Double) {
    implicit def ETH: COIN = count minUnit
  }

  implicit def wrapEthNum(count: Double): WRAPPER = make(count)
}

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

package hobby.chenai.nakam.autotx.core.token

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 29/05/2017
  */
object BtcZone extends AbsTokenZone {
  override type TOKEN = Token
  override type WRAPPER = Wrapper

  override def make(count: Long) = new Token(count)

  class Token private[BtcZone](count: Long) extends AbsToken(count: Long, unitName = "BTC")

  // 既是单位数据也是枚举
  lazy val CONG = 1 CONG
  lazy val BTC = 1 BTC
  lazy protected override val UNIT = BTC

  override def make(count: Double) = new Wrapper(count)

  class Wrapper(count: Double) extends AbsNumWrapper(count: Double) {
    implicit def CONG: TOKEN = count minUnit

    implicit def BTC: TOKEN = count * 100000000 CONG // 一亿聪
  }

  // 不可以写在父类里，否则对于多个不同的币种就不知道转换给谁了。
  implicit def wrapBtcNum(count: Double): WRAPPER = make(count)
}

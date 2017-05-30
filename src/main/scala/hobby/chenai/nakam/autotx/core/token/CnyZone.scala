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
  * @version 1.0, 30/05/2017
  */
object CnyZone extends AbsTokenZone {
  override type TOKEN = RMB
  override type WRAPPER = Wrapper

  override def make(count: Long) = new RMB(count)

  class RMB private[CnyZone](count: Long) extends AbsMoney(count: Long, unitName = "CNY")

  lazy val FEN = 1 FEN
  lazy val JIAO = 1 JIAO
  lazy val CNY = 1 CNY
  lazy protected override val UNIT = CNY

  override def make(count: Double) = new Wrapper(count)

  class Wrapper(count: Double) extends AbsNumWrapper(count: Double) {
    // 以下方法会递归的进行new Wrapper(x).xxx的调用
    implicit def FEN: TOKEN = count minUnit

    implicit def JIAO: TOKEN = count * 10 FEN

    implicit def CNY: TOKEN = count * 10 JIAO
  }

  implicit def wrapCnyNum(count: Double): WRAPPER = make(count)
}

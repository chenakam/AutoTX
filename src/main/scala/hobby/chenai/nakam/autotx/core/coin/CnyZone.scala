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
object CnyZone extends AbsCoinZone {
  override type COIN = RMB
  override type WRAPPER = Wrapper

  override def make(count: Long) = new RMB(count, "CNY")

  class RMB private[CnyZone](count: Long, unitName: String) extends AbsMoney(count: Long, unitName: String) {
    override def unit = CNY

    override def equals(obj: scala.Any) = obj match {
      case that: RMB => that.canEqual(this) && that.count == this.count
      case _ => false
    }

    override def canEqual(that: Any) = that.isInstanceOf[RMB]
  }

  lazy val FEN = new RMB(1, "FEN") {
    override def unit = this
  }
  lazy val JIAO = new RMB(10, "JIAO") {
    override def unit = this
  }
  lazy val CNY = 1 CNY

  override def make(count: Double) = new Wrapper(count)

  class Wrapper(count: Double) extends AbsNumWrapper(count: Double) {
    // 以下方法会递归的进行new Wrapper(x).xxx的调用
    implicit def FEN: COIN = count minUnit

    implicit def JIAO: COIN = count * 10 FEN

    implicit def CNY: COIN = count * 10 JIAO
  }

  implicit def wrapCnyNum(count: Double): WRAPPER = make(count)
}

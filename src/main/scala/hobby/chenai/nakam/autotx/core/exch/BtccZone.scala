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

package hobby.chenai.nakam.autotx.core.exch

import hobby.chenai.nakam.autotx.core.coin.{BtcGroup, CnyGroup}
import hobby.chenai.nakam.autotx.core.coin.BtcGroup._
import hobby.chenai.nakam.autotx.core.coin.CnyGroup._

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 04/06/2017
  */
object BtccZone extends AbsExchZone(BtcGroup, CnyGroup) {
  class Exchange extends AbsExchange("BTCC", BTC, CNY, BTC)

  lazy val BTCC = new Exchange
}

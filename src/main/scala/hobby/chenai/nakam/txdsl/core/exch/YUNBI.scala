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

package hobby.chenai.nakam.txdsl.core.exch

import hobby.chenai.nakam.txdsl.coin.{BtcToken, CnyCash, EthToken}
import hobby.chenai.nakam.txdsl.core.coin._
import hobby.chenai.nakam.txdsl.coin.BtcToken._
import hobby.chenai.nakam.txdsl.coin.CnyCash._
import hobby.chenai.nakam.txdsl.coin.EthToken._

import scala.language.postfixOps

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 31/05/2017
  */
object YUNBI extends AbsExchange("YUNBI", BtcToken, CnyCash, EthToken) {
  // 云币的手续费是这么收的：买到的或卖出的，从收入里面按比例扣除。即：买到的币扣币，卖出的钱扣钱。
  // 对于手续费，没有接受几位小数一说。
  updateTokenPricingRate(ETH, 0.09 BTC)
  updateCashPricingRate(ETH, 3000 CNY)

  override def loadFfdRule(counterParty: (AbsTokenGroup, AbsCoinGroup)): FixedFracDigitsRule = counterParty match {
    case (EthToken, BtcToken) => new FixedFracDigitsRule(EthToken, 6, BtcToken, 8, false)
    case (EthToken, CnyCash) => new FixedFracDigitsRule(EthToken, 6, CnyCash, 5, false)
    case (BtcToken, CnyCash) => new FixedFracDigitsRule(BtcToken, 8, CnyCash, 5, false)
  }
}

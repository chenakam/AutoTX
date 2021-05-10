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

import hobby.chenai.nakam.txdsl.core.coin.AbsCoinGroup
import hobby.chenai.nakam.util.NumFmt

/**
  * 接受小数位规则。
  * <P>
  * 对于不同的币种，交易时接受的Token自身小数位和定价币小数位各有不同。<br>
  * 对于同一个交易所，买和卖是一样的规则，可以用同一个本对象。
  *
  * @param tokenGroup       要买入或卖出的代币类型（Group）。
  * @param ffd4Token        tokenGroup 类型代币接受的小数位。相对于标准单位 `token.std.unit`。
  * @param pricingCoinGroup 定价货币类型，包括Token或Cash。
  * @param ffd4Pricing      定价货币接受的小数位。相对于标准单位 `coin.std.unit`。
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 18/07/2017;
  *          1.1, 10/05/2021, 去掉`cutTotal`参数，交易所不会截断两次，太极端。增加方法参数`ceiling`以满足实际需求。
  */
class FixedFracDigitsRule(val tokenGroup: AbsCoinGroup, val ffd4Token: Int, val pricingCoinGroup: AbsCoinGroup, val ffd4Pricing: Int) {
  def cut(token: tokenGroup.COIN): BigDecimal = token.std.valueFfd(fixedFracDigits = ffd4Token)

  def cut$(pricing: pricingCoinGroup.COIN): BigDecimal = pricing.std.valueFfd(fixedFracDigits = ffd4Pricing)

  /** @param ceiling `false`为保守计算，取下限（默认），`true`为精确计算或上限。 */
  def sell(token: tokenGroup.COIN, pricingExRate: pricingCoinGroup.COIN, ceiling: Boolean = false): pricingCoinGroup.COIN = {
    val value = if (ceiling) token.std.value * pricingExRate.std.value else cut(token) * cut$(pricingExRate)
    pricingCoinGroup.unitStd * NumFmt.cut2FixedFracDigits(value, ffd4Pricing, round = ceiling, up = ceiling)
  }

  /** @param ceiling `false`为保守计算，取下限（默认），`true`为精确计算或上限。 */
  def buy(cash: pricingCoinGroup.COIN, pricingExRate: pricingCoinGroup.COIN, ceiling: Boolean = false): tokenGroup.COIN = {
    val value = if (ceiling) cash.std.value / pricingExRate.std.value else cut$(cash) / cut$(pricingExRate)
    tokenGroup.unitStd * NumFmt.cut2FixedFracDigits(value, ffd4Token, round = ceiling, up = ceiling)
  }

  lazy val impl = coinTpeImpl[tokenGroup.COIN, pricingCoinGroup.COIN]
}

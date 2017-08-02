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

import hobby.chenai.nakam.autotx.core.coin.{AbsCoinGroup, AbsTokenGroup}
import hobby.chenai.nakam.lang.TypeBring
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
  * @param cutTotal         是否对买卖结果值的小数进行再次剪切。默认剪切，保守做法，否则对交易所不利。
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 18/07/2017
  */
class FixedFracDigitsRule(val tokenGroup: AbsTokenGroup, val ffd4Token: Int,
                          val pricingCoinGroup: AbsCoinGroup, val ffd4Pricing: Int,
                          val cutTotal: Boolean = true) {

  def cut(token: tokenGroup.COIN): Double = token.std.valueFfd(ffd4Token)

  def cut$(pricing: pricingCoinGroup.COIN): Double = pricing.std.valueFfd(ffd4Pricing)

  def sell(token: tokenGroup.COIN, pricingExRate: pricingCoinGroup.COIN): pricingCoinGroup.COIN = {
    val value = cut(token) * cut$(pricingExRate)
    pricingCoinGroup.unitStd * (if (cutTotal) NumFmt.cut2FixedFracDigits(value, ffd4Pricing) else value)
  }

  def buy(cash: pricingCoinGroup.COIN, pricingExRate: pricingCoinGroup.COIN): tokenGroup.COIN = {
    val value = cut$(cash) / cut$(pricingExRate)
    tokenGroup.unitStd * (if (cutTotal) NumFmt.cut2FixedFracDigits(value, ffd4Pricing) else value)
  }

  implicit lazy val ^ = new TypeBring[tokenGroup.COIN, tokenGroup.COIN, AbsTokenGroup#AbsToken] {}.t2
  implicit lazy val * = new TypeBring[pricingCoinGroup.COIN, pricingCoinGroup.COIN, AbsCoinGroup#AbsCoin] {}.t2
}

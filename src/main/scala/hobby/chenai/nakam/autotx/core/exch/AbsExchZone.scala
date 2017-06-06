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

import java.util.concurrent.ConcurrentHashMap

import hobby.chenai.nakam.autotx.core.coin.BtcZone._
import hobby.chenai.nakam.autotx.core.coin.{AbsCoinZone, BtcZone}

import scala.language.postfixOps

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 30/05/2017
  */
abstract class AbsExchZone {
  abstract class AbsExchange(val name: String, val pricingCash: AbsCoinZone#AbsCash, tokens: AbsCoinZone#AbsToken*) {
    val pricingBtc = BTC

    val supportTokens = (for (t <- tokens) yield t.std.unit).distinct
    require(supportTokens.nonEmpty)
    supportTokens.foreach(pricingCashRateMap.put(_, 0))

    def applyExch(src: AbsCoinZone#AbsCoin, dst: AbsCoinZone#Unt): AbsCoinZone#AbsCoin = {
      if ((supportTokens.contains(src.std.unit) || src.group == pricingCash.group)
        && (supportTokens.contains(dst.std.unit) || dst.group == pricingCash.group))
        ex.applyOrElse((src, dst, true), (x: (AbsCoinZone#AbsCoin, _, _)) => x._1)
      else src
    }

    /**
      * 有btc定价则优先btc, 否则强制法币定价（若没有则报错）。
      */
    protected lazy val ex: (AbsCoinZone#AbsCoin, AbsCoinZone#AbsCoin, Boolean) PartialFunction AbsCoinZone#AbsCoin = {
      case ref2tuple@(cash: AbsCoinZone#AbsCash, dst: AbsCoinZone#AbsCash, _) => cash mod dst.unit
      case (token: AbsCoinZone#AbsToken, dst: AbsCoinZone#AbsCash, promise) =>
        if (promise /*注意这个promise不能把任务再转给btc定价，不然会出现死递归*/ || pricingCashRateMap.containsKey(token))
          dst.std.unit * (token.std.value * getExRate(token, cash$btc = true)) mod dst.unit
        else token
      case (cash: AbsCoinZone#AbsCash, dst: AbsCoinZone#AbsToken, promise) =>
        if (promise || pricingCashRateMap.containsKey(dst))
          dst.std.unit * (cash.std.value / getExRate(dst, cash$btc = true)) mod dst.unit
        else cash
      case (btc: BtcZone.Token, dst: BtcZone.Token, _) => btc mod dst.unit
      case (token: AbsCoinZone#AbsToken, dst: BtcZone.Token, promise) =>
        if (pricingBtcRateMap.containsKey(token))
          dst.std.unit * (token.std.value * getExRate(token, cash$btc = false)) mod dst.unit
        else if (promise) ex.apply(ex.apply(token, pricingCash.std.unit, promise), dst, promise)
        else token
      case (btc: BtcZone.Token, dst: AbsCoinZone#AbsToken, promise) =>
        if (pricingBtcRateMap.containsKey(dst))
          dst.std.unit * (btc.std.value / getExRate(dst, cash$btc = false)) mod dst.unit
        else if (promise) ex.apply(ex.apply(btc, pricingCash.std.unit, promise), dst, promise)
        else btc
      case (src: AbsCoinZone#AbsToken, dst: AbsCoinZone#AbsToken, promise) =>
        if (dst.group == src.group) src mod dst.unit
        else {
          val bf = pricingBtcRateMap.containsKey(src)
          val bt = pricingBtcRateMap.containsKey(dst)
          val cf = pricingCashRateMap.containsKey(src)
          val ct = pricingCashRateMap.containsKey(dst)
          if (bf && bt) ex.apply(ex.apply(src, pricingBtc, false), dst, false)
          else if (cf && ct) ex.apply(ex.apply(src, pricingCash.std.unit, false), dst, false)
          else if (promise) {
            if (bf || bt) ex.apply(ex.apply(src, pricingBtc, true), dst, true)
            else ex.apply(ex.apply(src, pricingCash.std.unit, true), dst, true)
          } else src
        }
    }

    // 没有比特币汇率则必须有法币汇率
    /** 从加密货币到法币的汇率，而法币在一个交易所只有一种。 */
    private lazy val pricingCashRateMap = new ConcurrentHashMap[AbsCoinZone#Unt, Double]()
    /** 从加密货币到比特币的汇率。 */
    private lazy val pricingBtcRateMap = new ConcurrentHashMap[AbsCoinZone#Unt, Double]()

    def updateCashRate(token: AbsCoinZone#Unt, rate: Double): Double = pricingCashRateMap.put(token, rate)

    def updateBtcRate(token: AbsCoinZone#Unt, rate: Double): Double = pricingBtcRateMap.put(token, rate)

    def getExRate(token: AbsCoinZone#AbsToken, cash$btc: Boolean) = {
      val unit = token.std.unit
      val rate: Double = if (cash$btc) pricingCashRateMap.get(unit) else pricingBtcRateMap.get(unit)
      require(rate > 0, s"rate of ${token.group}(:${
        if (cash$btc) pricingCash.group
        else pricingBtc.group
      }) have not initialized on $name")
      rate
    }

    override def toString = s"$name(:$pricingCash)[${supportTokens.mkString(", ")}]"
  }
}

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

import hobby.chenai.nakam.autotx.core.coin.{AbsCashGroup, AbsCoinGroup, AbsTokenGroup}
import hobby.chenai.nakam.util.NumFmt
import java.util.concurrent.ConcurrentHashMap
import scala.language.postfixOps

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 30/05/2017
  */
abstract class AbsExchZone[+GPT <: AbsTokenGroup, +GPC <: AbsCashGroup](val pricingTG: GPT, val pricingCG: GPC) {
  /**
    * @param name         交易所名称。
    * @param pricingToken 定价的token。必须填，但只要不`updateTokenPricingRate()`就不会用它来计算定价。
    * @param pricingCash  定价的法币。
    * @param tokens       本平台支持的tokens。
    */
  abstract class AbsExchange
  (val name: String, val pricingToken: pricingTG.UNIT, val pricingCash: pricingCG.UNIT, tokens: AbsTokenGroup#UNIT*) {
    val supportTokens = (pricingToken.std.unit :: (for (t <- tokens) yield t.std.unit).toList).distinct
    supportTokens.foreach(cashPriRateMap.put(_, 0))
    // pricingTokenRateMap（即token定价）是可选的，因此不put into Map.
    // supportTokens.foreach(pricingTokenRateMap.put(_, 0))

    println(toString)

    /**
      *
      * @param coin
      * @return
      */
    def fixedFracDigits(coin: AbsCoinGroup#AbsCoin): Int = if (coin.isCash) ??? else -1

    def applyExch(src: AbsCoinGroup#AbsCoin, dst: AbsCoinGroup#Unt): AbsCoinGroup#AbsCoin = {
      // pricingToken已经在supportTokens里面了
      if ((supportTokens.contains(src.std.unit) || (src.group eq pricingCash.group))
        && (supportTokens.contains(dst.std.unit) || (dst.group eq pricingCash.group)))
        ex.applyOrElse((src, dst, true), (x: (AbsCoinGroup#AbsCoin, _, _)) => x._1)
      else src
    }

    /**
      * 有token(btc)定价则优先token, 否则强制法币定价（若没有则报错）。
      */
    protected lazy val ex: (AbsCoinGroup#AbsCoin, AbsCoinGroup#AbsCoin, Boolean) PartialFunction AbsCoinGroup#AbsCoin = {
      // pricingCash => pricingCash // 到这里不会出现两个不一样的法币
      case (cash: AbsCashGroup#AbsCash, dst: AbsCashGroup#AbsCash, _) => dst.unit << cash
      // token => pricingCash
      case (token: AbsTokenGroup#AbsToken, dst: AbsCashGroup#AbsCash, promise) =>
        if (promise /*注意这个promise不能把任务再转给token(btc)定价，不然会出现死递归*/ || cashPriRateMap.containsKey(token))
          dst.unit << (dst.std.unit * (token.std.valueFixedFD(fixedFracDigits(token)) * getExRate(token, false)))
        else token
      // pricingCash => token
      case (cash: AbsCashGroup#AbsCash, dst: AbsTokenGroup#AbsToken, promise) =>
        if (promise || cashPriRateMap.containsKey(dst))
          dst.unit << dst.std.unit * (cash.std.valueFixedFD(fixedFracDigits(cash)) / getExRate(dst, token$cash = false))
        else cash
      // pricingToken => pricingToken // 与cash不同，cash就一种，不需要判断。
      case (token: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, _)
        if (token.group eq pricingToken.group) && (dst.group eq pricingToken.group) => dst.unit << token
      // token => pricingToken
      case (token: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, promise) if dst.group eq pricingToken.group =>
        if (tokenPriRateMap.containsKey(token))
          dst.unit << dst.std.unit * (token.std.valueFixedFD(fixedFracDigits(token.std.unit)) * getExRate(token, true))
        else if (promise) ex.apply(ex.apply(token, pricingCash, promise), dst, promise)
        else token
      // pricingToken => token
      case (token: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, promise) if token.group eq pricingToken.group =>
        if (tokenPriRateMap.containsKey(dst))
          dst.unit << dst.std.unit * (token.std.valueFixedFD(fixedFracDigits(token)) / getExRate(dst, token$cash = true))
        else if (promise) ex.apply(ex.apply(token, pricingCash, promise), dst, promise)
        else token
      // token => token
      case (src: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, promise) =>
        if (dst.group == src.group) dst.unit << src
        else {
          val ptf = tokenPriRateMap.containsKey(src)
          val ptt = tokenPriRateMap.containsKey(dst)
          val cf = cashPriRateMap.containsKey(src)
          val ct = cashPriRateMap.containsKey(dst)
          if (ptf && ptt) ex.apply(ex.apply(src, pricingToken, false), dst, false)
          else if (cf && ct) ex.apply(ex.apply(src, pricingCash, false), dst, false)
          else if (promise) {
            if (ptf || ptt) ex.apply(ex.apply(src, pricingToken, true), dst, true)
            else ex.apply(ex.apply(src, pricingCash, true), dst, true)
          } else src
        }
    }

    // 没有比特币汇率则必须有法币汇率
    /** 从加密货币到法币的汇率，而法币在一个交易所只有一种。 */
    private lazy val cashPriRateMap = new ConcurrentHashMap[AbsCoinGroup#Unt, Double]()
    /** 从加密货币到比特币的汇率。 */
    private lazy val tokenPriRateMap = new ConcurrentHashMap[AbsCoinGroup#Unt, Double]()

    def updateCashPricingRate(token: AbsTokenGroup#UNIT, rate: Double): Double = {
      cashPriRateMap.put(requireSupports(token), NumFmt.cut2FixedFD(rate, x))
    }

    def updateTokenPricingRate(token: AbsTokenGroup#UNIT, rate: Double): Double = {
      tokenPriRateMap.put(requireSupports(token), rate)
    }

    private def requireSupports(token: AbsTokenGroup#UNIT): AbsTokenGroup#UNIT = {
      val unit = token.std.unit
      // 这里用 == 而不是 eq, 所以下面还必须返回unit.
      require(token == unit, s"`token` $token must be standard unit(token.std.unit) ${token.std.unit}.")
      require(supportTokens.contains(unit), s"parameter `token`.std.unit: $unit, " +
        s"is not contains in `supportTokens`: $supportTokenString.")
      unit
    }

    def getExRate(token: AbsTokenGroup#AbsToken, token$cash: Boolean) = {
      val unit = token.std.unit
      val rate: Double = if (token$cash) tokenPriRateMap.get(unit) else cashPriRateMap.get(unit)
      require(rate > 0, s"rate of ${token.group}(:${
        if (token$cash) pricingToken.group else pricingCash.group
      }) have not initialized on $name.")
      rate
    }

    override def toString = s"$name(:$pricingCash)$supportTokenString"

    def supportTokenString = supportTokens.mkString("[", ", ", "]")
  }
}

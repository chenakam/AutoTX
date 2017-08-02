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

import hobby.chenai.nakam.autotx.core.coin._
import hobby.chenai.nakam.tool.cache.LruMemSyncGet
import java.util.concurrent.ConcurrentHashMap
import scala.language.postfixOps

/**
  * @param name         交易所名称。
  * @param pricingToken 定价的token。必须填，但只要不`updateTokenPricingRate()`就不会用它来计算定价。
  * @param pricingCash  定价的法币。
  * @param tokens       本平台支持的tokens。
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 30/05/2017
  */
abstract class AbsExchange
(val name: String, val pricingToken: AbsTokenGroup, val pricingCash: AbsCashGroup, tokens: AbsTokenGroup*)
  extends LruMemSyncGet[(AbsTokenGroup, AbsCoinGroup), FixedFracDigitsRule] {

  val supportTokens = tokens.toSet + pricingToken
  private val zero = pricingCash.unitStd * 0
  supportTokens.foreach(cashPriRateMap.put(_, zero))
  // tokenPriRateMap（即token定价）是可选的，因此不put into Map.
  // supportTokens.foreach(tokenPriRateMap.put(_, 0))

  println("init:" + toString)

  override protected def maxCacheSize = 10

  final def getFfdRule[T <: AbsTokenGroup, C <: AbsCoinGroup](token: T, pricingCoin: C): FixedFracDigitsRule =
    get(token, pricingCoin).asInstanceOf[FixedFracDigitsRule]

  def applyExch(src: AbsCoinGroup#AbsCoin, dst: AbsCoinGroup#Unt): AbsCoinGroup#AbsCoin = {
    // pricingToken已经在supportTokens里面了
    if ((if (src.isCash) src.group eq pricingCash else supportTokens.contains(src.group.asInstanceOf[AbsTokenGroup]))
      && (if (dst.isCash) dst.group eq pricingCash else supportTokens.contains(dst.group.asInstanceOf[AbsTokenGroup])))
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
      if (promise /*注意这个promise不能把任务再转给token(btc)定价，不然会出现死递归*/ || cashPriRateMap.containsKey(token)) {
        val ffdRule = getFfdRule(token.group, dst.group)
        import ffdRule._
        dst.unit << sell(token, getExRate(token.group, token$cash = false))
      } else token
    // pricingCash => token
    case (cash: AbsCashGroup#AbsCash, dst: AbsTokenGroup#AbsToken, promise) =>
      if (promise || cashPriRateMap.containsKey(dst)) {
        val ffdRule = getFfdRule(dst.group, cash.group)
        import ffdRule._
        dst.unit << buy(cash /*注意这里不是dst, cash表示拿了多少钱*/ , getExRate(dst.group, token$cash = false))
      } else cash
    // pricingToken => pricingToken // 与token不同，cash就一种，不需要判断。
    case (token: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, _)
      if (token.group eq pricingToken) && (dst.group eq pricingToken) => dst.unit << token
    // token => pricingToken
    case (token: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, promise) if dst.group eq pricingToken =>
      if (tokenPriRateMap.containsKey(token)) {
        val ffdRule = getFfdRule(token.group, dst.group)
        import ffdRule._
        dst.unit << sell(token, getExRate(token.group, token$cash = true))
      } else if (promise) ex.apply(ex.apply(token, pricingCash.unitStd, promise), dst, promise)
      else token
    // pricingToken => token
    case (token: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, promise) if token.group eq pricingToken =>
      if (tokenPriRateMap.containsKey(dst)) {
        val ffdRule = getFfdRule(dst.group, token.group)
        import ffdRule._
        dst.unit << buy(token /*注意这里不是dst, token表示拿了多少钱*/ , getExRate(dst.group, token$cash = true))
      } else if (promise) ex.apply(ex.apply(token, pricingCash.unitStd, promise), dst, promise)
      else token
    // token => token
    case (src: AbsTokenGroup#AbsToken, dst: AbsTokenGroup#AbsToken, promise) =>
      if (dst.group == src.group) dst.unit << src
      else {
        val ptf = tokenPriRateMap.containsKey(src)
        val ptt = tokenPriRateMap.containsKey(dst)
        val cf = cashPriRateMap.containsKey(src)
        val ct = cashPriRateMap.containsKey(dst)
        if (ptf && ptt) ex.apply(ex.apply(src, pricingToken.unitStd, false), dst, false)
        else if (cf && ct) ex.apply(ex.apply(src, pricingCash.unitStd, false), dst, false)
        else if (promise) {
          if (ptf || ptt) ex.apply(ex.apply(src, pricingToken.unitStd, true), dst, true)
          else ex.apply(ex.apply(src, pricingCash.unitStd, true), dst, true)
        } else src
      }
  }

  // 没有比特币汇率则必须有法币汇率
  /** 从加密货币到法币的汇率，而法币在一个交易所只有一种。 */
  private lazy val cashPriRateMap = new ConcurrentHashMap[AbsTokenGroup, pricingCash.COIN]()
  /** 从加密货币到比特币的汇率。 */
  private lazy val tokenPriRateMap = new ConcurrentHashMap[AbsTokenGroup, pricingToken.COIN]()

  def updateCashPricingRate(tokenGroup: AbsTokenGroup, rate: pricingCash.COIN): Unit = {
    cashPriRateMap.put(requireSupports(tokenGroup), rate)
  }

  def updateTokenPricingRate(tokenGroup: AbsTokenGroup, rate: pricingToken.COIN): Unit = {
    tokenPriRateMap.put(requireSupports(tokenGroup), rate)
  }

  private def requireSupports(tokenGroup: AbsTokenGroup): AbsTokenGroup = {
    require(supportTokens.contains(tokenGroup), s"parameter `tokenGroup`: $tokenGroup, " +
      s"is not contains in `supportTokens`: $supportTokenString.")
    tokenGroup
  }

  def getExRate(tokenGroup: AbsTokenGroup, token$cash: Boolean) = {
    val rate = if (token$cash) tokenPriRateMap.get(tokenGroup) else cashPriRateMap.get(tokenGroup)
    require(rate != null && rate.value > 0, s"rate of $tokenGroup(:${
      if (token$cash) pricingToken else pricingCash
    }) have not initialized on $name.")
    rate
  }

  override def toString = s"$name(:$pricingCash|$pricingToken)$supportTokenString"

  def supportTokenString = supportTokens.mkString("[", ", ", "]")
}

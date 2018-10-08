/*
 * Copyright (C) 2018-present, Chenai Nakam(chenai.nakam@gmail.com)
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

package hobby.chenai.nakam.autotx

import hobby.chenai.nakam.autotx.core.coin.{AbsCashGroup, AbsTokenGroup, CnyGroup}
import hobby.chenai.nakam.autotx.core.coin.EthGroup._
import hobby.chenai.nakam.autotx.task.{kces, CounterParty, RealTimePriceScheduler}
import hobby.wei.c.reflow
import hobby.wei.c.reflow.{Out, Reflow}
import hobby.wei.c.reflow.implicits._
import scala.language.postfixOps

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 10/07/2018
  */
object AutoTx {
  def startOnAll(): Unit = SupportedExchange.values.foreach(_ => startOn(_))

  def startOn(exchange: SupportedExchange.Tpe): Unit = {
    Reflow.create(CounterParty(exchange))
      .next(RealTimePriceScheduler())
      .submit("启动器", none)
      .start((kces.exchangeName, exchange.name), new reflow.Feedback.Adapter {
        override def onComplete(out: Out): Unit = {
          Reflow.create()
        }
      })
  }

  // TODO: 在独立进程实时运算
  load市场实时数据()
  Analyzer4CNY.分析优质币()

  object Analyzer4CNY extends Analyzer(CnyGroup)

  abstract class Analyzer(protected val cash: AbsCashGroup) {
    def entryOrdersIntelligent(): Unit = {
      val (yes, mount) = 是否继续投入资金
      if (yes) {
        val 优质币种 = 筛选出指数符合用户设置(分析优质币())
        val 目标 = 具备最佳时机的(优质币种)
        if (目标.nonEmpty) autoEntryOrdersOfBuying(mount, 目标)
        else {
          // 等待事件触发调用：余额变动，用户设置更改，用户手动触发
        }
      } else {
        // 等待事件触发调用：余额变动，用户设置更改，用户手动触发。
      }
    }

    // 具体的一个订单
    def entryOrderOfBuying(money: AbsCashGroup#AbsCash, token: AbsTokenGroup): Unit = {
      // TODO: http 请求参数，用户是否注册，apiKey/apiSecret。
    }

    def 具备最佳时机的(优质币: Seq[(AbsTokenGroup, Double)]): Seq[(AbsTokenGroup, Double /*建议购买比例*/ )] = {
      // 分析舆论情况。牛市 or 熊市。
      // 时机是什么？哪个最有暴涨的趋势（最小的代价可以拉起来）。
      if (优质币.isEmpty) Nil else Nil
    }

    def autoEntryOrdersOfBuying(金额: AbsCashGroup#AbsCash, 优质币种: Seq[(AbsTokenGroup, Double)]): Unit = {
      优质币种.foreach { 币 =>
        // TODO: 拆分成几单，需要计算最优解。
        val each = 金额 / 10
        (0 until 10).foreach { _ => entryOrderOfBuying(each * 币._2, 币._1) }
      }
    }

    def 分析优质币(): Seq[(AbsTokenGroup, Double /*增长指数（综合评分）*/ )] = {
      // 单位时间内（一天？一小时？）上涨概率、涨幅。需要把上述参数综合评估。
      (ETH.group, 0.65) :: Nil
    }

    def 筛选出指数符合用户设置(优质币: Seq[(AbsTokenGroup, Double)]): Seq[(AbsTokenGroup, Double /*增长指数（综合评分）*/ )] = {
      优质币
    }

    /** 是否继续投入资金。 */
    def 是否继续投入资金: (Boolean, AbsCashGroup#AbsCash) = (可用现金额 >= 可挂单最低现金额, 可用现金额)

    def 可挂单最低现金额: cash.COIN = ???

    def 可用现金额: cash.COIN = 现金总余额 - 现金备用预留

    def 现金总余额: cash.COIN = ???

    def 现金备用预留: cash.COIN = ???
  }

  def load交易对And计价规则(): Unit = {
    // TODO: 存入数据库，更新频率1天1次。可强制刷新。
  }

  def load市场实时数据(): Unit = {
    // TODO: 根据限定频率（如5分钟600次调用）实时刷新数据。存库以供分析。
  }
}

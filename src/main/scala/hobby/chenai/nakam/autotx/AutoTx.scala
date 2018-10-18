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
import hobby.chenai.nakam.autotx.task.{kces, RealTimePriceScheduler}
import hobby.wei.c.reflow
import hobby.wei.c.reflow.{Config, Out, Reflow}
import hobby.wei.c.reflow.implicits._

import scala.language.postfixOps

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 10/07/2018
  */
object AutoTx {
  def 拉取实时价格(): Unit = {
  }

  def 挂单(exchange: Exchange.Tpe, couPty: CounterParty): Unit = {
  }

  def 撤单(exchange: Exchange.Tpe, couPty: CounterParty): Unit = {
  }

  def 拉取挂单列表(exchange: Exchange.Tpe): Unit = {
  }

  def 拉取成交列表(exchange: Exchange.Tpe): Unit = {
  }

  def startOnAll(): Unit = Exchange.values.foreach(_ => startOn(_))

  def startOn(exchange: Exchange.Tpe): Unit = {
    Reflow.create(task.CounterParty(exchange))
      .next(RealTimePriceScheduler())
      .submit("启动器", none)
      .start((kces.exchangeName, exchange.name), new reflow.Feedback.Adapter {
        override def onComplete(out: Out): Unit = {
          //          Reflow.create()
        }
      })
  }

  /** 没有包装在`ProcessX`里的是留给主进程或`UI`进程去调用的。
    * 固定进程的代码应该由`特定进程`的`服务`或`UI`去触发和调用。 */
  object Process4RealTimePrice extends TaskProcess(Config.apply(2, 5, 30)) {
    // TODO: 在独立进程实时运算
    load市场实时数据()

    // TODO 应当先写各种功能，然后再拿到这里组合起来。
  }
  object Process4Analysis {
    Analyzer4CNY.分析优质币()
  }

  // 只考虑赚取`BTC`或者法币锚定币。
  object Analyzer4CNY extends Analyzer(Exchange.BinAn, CnyGroup)

  abstract class Analyzer(val exchange: Exchange.Tpe, val cash: AbsCashGroup) {
    def entryOrdersIntelligent(): Unit = {
      val (yes, mount) = 是否继续投入资金()
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
    def entryOrderOfBuying(money: AbsCashGroup#COIN, token: AbsTokenGroup): Unit = {
      // TODO: http 请求参数，用户是否注册，apiKey/apiSecret。
    }

    def 具备最佳时机的(优质币: Seq[(AbsTokenGroup, Double)]): Seq[(AbsTokenGroup, Double /*建议购买比例*/ )] = {
      // 分析舆论情况。牛市 or 熊市。
      // 时机是什么？哪个最有暴涨的趋势（最小的代价可以拉起来）。
      if (优质币.isEmpty) Nil else Nil
    }

    def autoEntryOrdersOfBuying(金额: AbsCashGroup#COIN, 优质币种: Seq[(AbsTokenGroup, Double)]): Unit = {
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
    def 是否继续投入资金(): (Boolean, AbsCashGroup#COIN) = {
      val mount = 可用现金额()
      (mount >= 可挂单最低现金额(), mount)
    }

    def 可挂单最低现金额(): cash.COIN = ??? // exchange 跟交易所有关

    /** 现金通常指：Pricing Token（定价币，如：BTC, USDT等）。 */
    def 可用现金额(): cash.COIN = 现金总余额() - 现金备用预留()

    def 现金总余额(): cash.COIN = ???

    def 现金备用预留(): cash.COIN = ???
  }

  def load交易对And计价规则(): Unit = {
    // TODO: 存入数据库，更新频率1天1次。可强制刷新。
  }

  def load市场实时数据(): Unit = {
    // TODO: 根据限定频率（如5分钟600次调用）实时刷新数据。存库以供分析。
  }
}

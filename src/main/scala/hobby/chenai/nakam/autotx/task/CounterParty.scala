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

package hobby.chenai.nakam.autotx.task

import hobby.chenai.nakam.autotx.AutoTx
import hobby.wei.c.reflow
import hobby.wei.c.reflow.implicits._

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 11/07/2018
  */
object CounterParty {
  def apply(exchange: AutoTx.SupportedExchange.Tpe): reflow.Trait = new MTrait(exchange)

  class MTrait(exchange: AutoTx.SupportedExchange.Tpe) extends reflow.Trait.Adapter {
    override protected def name() = getClass.getName

    override def newTask() = new MTask

    override protected def requires() = kces.refreshFromNet + kces.exchangeName

    override protected def outs() = kces.exchangeName + kces.counterPartySeq

    override protected def priority() = P_NORMAL

    override protected def period() = SHORT

    override protected def desc() = s"加载交易所`${exchange.name}`的交易对"
  }

  class MTask extends reflow.Task {
    override protected def doWork(): Unit = {
      // TODO: 下载存库，一般从本地加载，择机从网络刷新，更新频率1天1次。可强制刷新。

      // kces.counterPartyUrl 这个也由当前任务读取。

      // 也可以只读用户关注的交易对

      // TODO: 考虑 isAborted
    }
  }
}

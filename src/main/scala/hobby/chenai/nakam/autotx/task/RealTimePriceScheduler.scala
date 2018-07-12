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

import hobby.wei.c.reflow
import hobby.wei.c.reflow.implicits._

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 11/07/2018
  */
object RealTimePriceScheduler {
  def apply(): reflow.Trait = new MTrait()

  class MTrait extends reflow.Trait.Adapter {
    override protected def name() = getClass.getName

    override def newTask() = new MTask

    override protected def requires() = kces.counterPartySeq + kces.counterPartyUserPrefer

    override protected def outs() = none

    override protected def priority() = P_NORMAL

    override protected def period() = SHORT

    override protected def desc() = "加载指定交易所的交易对"
  }

  class MTask extends reflow.Task {
    override protected def doWork(): Unit = {
      // TODO: 当前是调度器。
      // TODO: 可能存在交易对太多的情况，需要并行下载。同时优先下载用户关注的。以及靠谱程度较高的。

      // TODO: 这个任务可能成为一个负责加载数据调度的任务：动态创建并行任务。
    }
  }
}

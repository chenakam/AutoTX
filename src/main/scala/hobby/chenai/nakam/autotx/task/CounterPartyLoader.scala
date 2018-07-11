package hobby.chenai.nakam.autotx.task

import hobby.wei.c.reflow
import hobby.wei.c.reflow.implicits._

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 11/07/2018
  */
object CounterPartyLoader {
  class MTrait extends reflow.Trait.Adapter {
    override protected def name() = "CounterPartyLoader"

    override def newTask() = new MTask

    override protected def requires() = kces.exchangeName

    override protected def outs() = kces.exchangeName + kces.counterPartySeq

    override protected def priority() = P_NORMAL

    override protected def period() = SHORT

    override protected def desc() = "从交易所加载交易对"
  }

  class MTask extends reflow.Task {
    override protected def doWork(): Unit = {
      // TODO: 下载存库，一般从本地加载，择机从网络刷新，更新频率1天1次。可强制刷新。

      // kces.counterPartyUrl 这个也由当前任务读取。

      // TODO: 考虑 isAborted
    }
  }
}

/*
 * Copyright (C) 2019-present, Chenai Nakam(chenai.nakam@gmail.com)
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

import hobby.chenai.nakam.autotx.coin.AdaGroup._
import hobby.chenai.nakam.autotx.coin.BtcGroup._
import hobby.chenai.nakam.autotx.coin.CnyGroup._
import hobby.chenai.nakam.autotx.coin.EthGroup._
import hobby.chenai.nakam.autotx.coin.UsdtGroup._
import hobby.chenai.nakam.autotx.core.coin.AbsCoinGroup

import scala.language.postfixOps

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 10/05/2019
  */
package object coin {
  object Serializer extends (AbsCoinGroup#AbsCoin => (Double, String)) {
    def apply(coin: AbsCoinGroup#AbsCoin): (Double, String) = {
      val std = coin.std
      (std.value, std.unit.name)
    }

    //@throws[RuntimeException]
    def unapply(coin: (Double, String)): Option[AbsCoinGroup#AbsCoin] = Option(coin._2 match {
      case CNY.name => coin._1 CNY
      case USDT.name => coin._1 USDT
      case BTC.name => coin._1 BTC
      case ETH.name => coin._1 ETH
      case ADA.name => coin._1 ADA
      // TODO:下一个任务，增加 ADA 等一系列主流币种。
      case _ => null // 交易所有很多币种，简单地抛出异常是不行的。 throw new RuntimeException(s"不存在指定的币种，或不是[标准]单位:$coin".tag)
    })
  }

  implicit class Serializable(coin: AbsCoinGroup#AbsCoin) {
    def serialize: (Double, String) = Serializer(coin)
  }

  implicit class Deserializable(coin: (Double, String)) {
    //@throws[RuntimeException]
    def desrl: Option[AbsCoinGroup#AbsCoin] = (coin /*: @unchecked*/) match {
      case Serializer(x) => Some(x)
      case _ => None
    }
  }
}

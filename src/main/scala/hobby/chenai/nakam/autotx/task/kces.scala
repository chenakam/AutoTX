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

import hobby.wei.c.reflow.Kce

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 11/07/2018
  */
object kces {
  lazy val refreshFromNet = new Kce[String]("refresh_from_net") {}

  /** 交易所名称。 */
  lazy val exchangeName = new Kce[String]("exchange_name") {}

  // TODO: 还有一些header`apiKey/apiSecret`之类的参数，封装成任务读取。

  /** `exchangeName`对应的交易所下的交易对请求地址。 */
  // 由任务自己读取。
  // lazy val counterPartyUrl = new Kce[String]("counter_party_url") {}

  /** 交易对列表。 */
  lazy val counterPartySeq = new Kce[Seq[(String, String)]]("counter_party_seq") {}

  /** 用户关注的交易对列表。 */
  lazy val counterPartyUserPrefer = new Kce[Seq[(String, String)]]("counter_party_user_prefer") {}
  /** 用户关注的交易对列表。 */
  lazy val realTimeData = new Kce[Seq[(String, String)]]("real_time_data") {}
}

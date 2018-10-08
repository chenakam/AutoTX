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

package hobby.chenai.nakam

import hobby.wei.c.reflow.Feedback.Progress.Policy
import hobby.wei.c.reflow.{Poster, Reflow}

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 06/10/2018
  */
package object autotx {
  // TODO: 注意在 Android 平台上需要修改。
  // Reflow.setThreadResetor(new ThreadResetor {})
  // Reflow.setLogger()
  Reflow.setDebugMode(true)

  implicit lazy val policy: Policy = Policy.Depth(3) -> Policy.Fluent -> Policy.Interval(600)
  implicit lazy val poster: Poster = null

  object SupportedExchange extends Enumeration {
    type Tpe = Exchange
    private[SupportedExchange] case class Exchange(name: String) extends Val
    val HuoBi = Exchange("HuoBi")
  }
}

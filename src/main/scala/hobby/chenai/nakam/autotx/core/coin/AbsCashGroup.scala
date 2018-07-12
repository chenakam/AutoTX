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

package hobby.chenai.nakam.autotx.core.coin

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 11/06/2017
  */
abstract class AbsCashGroup extends AbsCoinGroup {
  override protected[coin] type GROUP = AbsCashGroup
  override type COIN <: AbsCash

  @deprecated
  abstract class AbsCoin(count: Long) extends super.AbsCoin(count: Long)

  abstract class AbsCash(count: Long) extends super.AbsCoin(count: Long) {
    final lazy val isCash = true
  }
}

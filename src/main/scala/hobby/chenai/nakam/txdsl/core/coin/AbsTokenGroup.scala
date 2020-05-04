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

package hobby.chenai.nakam.txdsl.core.coin

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 11/06/2017
  */
abstract class AbsTokenGroup extends AbsCoinGroup {
  override type GROUP <: AbsTokenGroup
  override type COIN <: AbsCoin

  abstract class AbsCoin(count: BigInt) extends super.AbsCoin(count: BigInt) {
    final /*lazy*/ val isCash = false
  }
}

///*
// * Copyright (C) 2017-present, Chenai Nakam(chenai.nakam@gmail.com)
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package hobby.chenai.nakam.txdsl.core.order
//
//import hobby.chenai.nakam.txdsl.core.Rule
//
///**
//  * @param id    挂单id
//  * @param rule  交易规则
//  * @param num   数量
//  * @param price 购买单价
//  * @author Chenai Nakam(chenai.nakam@gmail.com)
//  * @version 1.0, 25/05/2017
//  */
//class Item(val id: String,
//           val rule: Rule,
//           val num: Float,
//           val price: Float) {
//  lazy val fees = rule.feesFixed + price * num * rule.feesPerct
//
//  //  def fees(price: Float, num: Float) = {
//  //    feesFSrc match {
//  //      case TOKEN => price * feesFixed // 给固定的token数量
//  //      case _ => feesFixed
//  //    }
//  //  } + {
//  //    feesPSrc match {
//  //      case _ => price * num * feesPerct // 给价值的百分比
//  //    }
//  //  }
//}

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
//package hobby.chenai.nakam.txdsl.core
//
//import hobby.chenai.nakam.txdsl.core.FeeSrc.FeeSrc
//import hobby.chenai.nakam.txdsl.core.coin.AbsCoinZone
//
///**
//  * @param token     代币类型
//  * @param feesFixed 固定单笔交易费用（平台回扣）
//  * @param feesFSrc  feesFixed的费用单位
//  * @param feesPerct 按比例收取的费用（平台回扣）
//  * @param feesPSrc  feesPerct的费用单位
//  * @param buy       true买false卖
//  * @author Chenai Nakam(chenai.nakam@gmail.com)
//  * @version 1.0, 25/05/2017
//  */
//class Rule private[core](val token: AbsCoinZone#AbsToken,
//                         val feesFixed: Float,
//                         val feesFSrc: FeeSrc,
//                         val feesPerct: Float,
//                         val feesPSrc: FeeSrc,
//                         val buy: Boolean)
//
//object Buy {
//  def apply(token: Token,
//            feesFixed: Float, feesFSrc: FeeSrc,
//            feesPerct: Float, feesPSrc: FeeSrc)
//  = new Rule(exchange, token, feesFixed, feesFSrc, feesPerct, feesPSrc, true)
//
//  def apply(exchange: Exchange, token: Token,
//            feesFixed: Float,
//            feesPerct: Float, feeSrc: FeeSrc): Rule
//  = apply(exchange, token, feesFixed, feeSrc, feesPerct, feeSrc)
//
//  lazy val YUN_BI_BTC = Buy()
//}
//
//object Sale {
//  def apply(exchange: Exchange, token: Token,
//            feesFixed: Float, feesFSrc: FeeSrc,
//            feesPerct: Float, feesPSrc: FeeSrc)
//  = new Rule(exchange, token, feesFixed, feesFSrc, feesPerct, feesPSrc, false)
//
//  def apply(exchange: Exchange, token: Token,
//            feesFixed: Float,
//            feesPerct: Float, feeSrc: FeeSrc): Rule
//  = apply(exchange, token, feesFixed, feeSrc, feesPerct, feeSrc)
//}

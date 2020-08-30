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

package hobby.chenai.nakam.txdsl

import hobby.chenai.nakam.txdsl.coin.AdaToken._
import hobby.chenai.nakam.txdsl.coin.AeToken._
import hobby.chenai.nakam.txdsl.coin.AkroToken._
import hobby.chenai.nakam.txdsl.coin.AlgoToken._
import hobby.chenai.nakam.txdsl.coin.AxisToken._
import hobby.chenai.nakam.txdsl.coin.BchToken._
import hobby.chenai.nakam.txdsl.coin.BsvToken._
import hobby.chenai.nakam.txdsl.coin.BtcToken._
import hobby.chenai.nakam.txdsl.coin.CnyCash._
import hobby.chenai.nakam.txdsl.coin.CreditToken._
import hobby.chenai.nakam.txdsl.coin.CrvToken._
import hobby.chenai.nakam.txdsl.coin.CsToken._
import hobby.chenai.nakam.txdsl.coin.DddToken._
import hobby.chenai.nakam.txdsl.coin.DfToken._
import hobby.chenai.nakam.txdsl.coin.DkaToken._
import hobby.chenai.nakam.txdsl.coin.DogeToken._
import hobby.chenai.nakam.txdsl.coin.DotToken._
import hobby.chenai.nakam.txdsl.coin.ElfToken._
import hobby.chenai.nakam.txdsl.coin.EosToken._
import hobby.chenai.nakam.txdsl.coin.EthToken._
import hobby.chenai.nakam.txdsl.coin.FilToken._
import hobby.chenai.nakam.txdsl.coin.GardToken._
import hobby.chenai.nakam.txdsl.coin.GtToken._
import hobby.chenai.nakam.txdsl.coin.IotaToken._
import hobby.chenai.nakam.txdsl.coin.IrisToken._
import hobby.chenai.nakam.txdsl.coin.KlayToken._
import hobby.chenai.nakam.txdsl.coin.KsmToken._
import hobby.chenai.nakam.txdsl.coin.LbaToken._
import hobby.chenai.nakam.txdsl.coin.LtcToken._
import hobby.chenai.nakam.txdsl.coin.MiniToken._
import hobby.chenai.nakam.txdsl.coin.NknToken._
import hobby.chenai.nakam.txdsl.coin.OntToken._
import hobby.chenai.nakam.txdsl.coin.QtumToken._
import hobby.chenai.nakam.txdsl.coin.RdnToken._
import hobby.chenai.nakam.txdsl.coin.RlcToken._
import hobby.chenai.nakam.txdsl.coin.SaltToken._
import hobby.chenai.nakam.txdsl.coin.SrmToken._
import hobby.chenai.nakam.txdsl.coin.SushiToken._
import hobby.chenai.nakam.txdsl.coin.UmaToken._
import hobby.chenai.nakam.txdsl.coin.UsdtToken._
import hobby.chenai.nakam.txdsl.coin.WnxmToken._
import hobby.chenai.nakam.txdsl.coin.Yam2Token._
import hobby.chenai.nakam.txdsl.coin.YfiiToken._
import hobby.chenai.nakam.txdsl.coin.YfiToken._
import hobby.chenai.nakam.txdsl.coin.YfvToken._
import hobby.chenai.nakam.txdsl.coin.ZecToken._
import hobby.chenai.nakam.txdsl.coin.ZrxToken._
import hobby.chenai.nakam.txdsl.core.coin.AbsCoinGroup

import scala.language.{implicitConversions, postfixOps}

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 10/05/2019
  */
package object coin {
  object Serializer extends (AbsCoinGroup#AbsCoin => (BigDecimal, String)) {
    def apply(coin: AbsCoinGroup#AbsCoin): (BigDecimal, String) = {
      val std = coin.std
      (std.value, std.unit.name)
    }

    //@throws[RuntimeException]
    def unapply(coin: (BigDecimal, String)): Option[AbsCoinGroup#AbsCoin] = Option(coin._2 match {
      case CNY.name => coin._1 CNY
      case USDT.name => coin._1 USDT
      case BTC.name => coin._1 BTC
      case ETH.name => coin._1 ETH
      case ADA.name => coin._1 ADA
      case AE.name => coin._1 AE
      case ALGO.name => coin._1 ALGO
      case BCH.name => coin._1 BCH
      case BSV.name => coin._1 BSV
      case CREDIT.name => coin._1 CREDIT
      case CRV.name => coin._1 CRV
      case CS.name => coin._1 CS
      case DDD.name => coin._1 DDD
      case DOGE.name => coin._1 DOGE
      case DOT.name => coin._1 DOT
      case ELF.name => coin._1 ELF
      case EOS.name => coin._1 EOS
      case FIL.name => coin._1 FIL
      case GARD.name => coin._1 GARD
      case GT.name => coin._1 GT
      case IOTA.name => coin._1 IOTA
      case KLAY.name => coin._1 KLAY
      case KSM.name => coin._1 KSM
      case LBA.name => coin._1 LBA
      case LTC.name => coin._1 LTC
      case NKN.name => coin._1 NKN
      case ONT.name => coin._1 ONT
      case QTUM.name => coin._1 QTUM
      case RDN.name => coin._1 RDN
      case RLC.name => coin._1 RLC
      case SALT.name => coin._1 SALT
      case YAMV2.name => coin._1 YAMV2
      case YFI.name => coin._1 YFI
      case ZEC.name => coin._1 ZEC
      case ZRX.name => coin._1 ZRX
      case AKRO.name => coin._1 AKRO
      case AXIS.name => coin._1 AXIS
      case DF.name => coin._1 DF
      case DKA.name => coin._1 DKA
      case IRIS.name => coin._1 IRIS
      case MINI.name => coin._1 MINI
      case SRM.name => coin._1 SRM
      case SUSHI.name => coin._1 SUSHI
      case UMA.name => coin._1 UMA
      case WNXM.name => coin._1 WNXM
      case YFII.name => coin._1 YFII
      case YFV.name => coin._1 YFV
      case _ => null // 交易所有很多币种，简单地抛出异常是不行的。 throw new RuntimeException(s"不存在指定的币种，或不是[标准]单位:$coin".tag)
    })
  }

  implicit class Serializable(coin: AbsCoinGroup#AbsCoin) {
    def serialize: (BigDecimal, String) = Serializer(coin)
  }

  implicit class Deserializable(coin: (BigDecimal, String)) {
    //@throws[RuntimeException]
    def desrl: Option[AbsCoinGroup#AbsCoin] = (coin /*: @unchecked*/) match {
      case Serializer(tuple) => Some(tuple)
      case _ => None
    }
  }

  implicit class Str2BigInt(count: String) {
    @inline def bigInt: BigInt = string2BigInt(count)
  }

  implicit class BigDec2BigInt(count: BigDecimal) {
    @inline def bigInt: BigInt = bigDecimal2BigInt(count)
  }

  implicit class Str2BigDec(count: String) {
    @inline def bigDec: BigDecimal = string2BigDecimal(count)
  }

  implicit class BigInt2BigDec(count: BigInt) {
    @inline def bigDec: BigDecimal = bigInt2BigDecimal(count)
  }

  @inline implicit def string2BigInt(count: String): BigInt = BigInt(count)
  @inline implicit def bigDecimal2BigInt(count: BigDecimal): BigInt = count.toBigInt()
  @inline implicit def string2BigDecimal(count: String): BigDecimal = BigDecimal(count)
  @inline implicit def bigInt2BigDecimal(count: BigInt): BigDecimal = BigDecimal(count)
}

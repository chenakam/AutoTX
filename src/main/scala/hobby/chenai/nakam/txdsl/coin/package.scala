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

import hobby.chenai.nakam.txdsl.core.coin.AbsCoinGroup
import scala.language.{implicitConversions, postfixOps}

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 10/05/2019
  */
package object coin {
  /*为了用一个`import`就能把所有功能导入，而不用许多个`import`。*/

  lazy val BTC                                             = BtcToken.BTC
  lazy val SAT                                             = BtcToken.SAT
  implicit lazy val _BTC_i: Int => BtcToken.DslImplInt     = BtcToken.wrapBtcNum
  implicit lazy val _BTC_s: String => BtcToken.DslImpl     = _BTC_d(_)
  implicit lazy val _BTC_b: Double => BtcToken.DslImpl     = BtcToken.wrapBtcNum
  implicit lazy val _BTC_d: BigDecimal => BtcToken.DslImpl = BtcToken.wrapBtcNum
  // implicit lazy val _btc_d = BtcToken.wrapBtcNum(_: BigDecimal)

  lazy val ETH                                             = EthToken.ETH
  lazy val GWei                                            = EthToken.GWei
  lazy val Wei                                             = EthToken.Wei
  implicit lazy val _ETH_i: Int => EthToken.DslImplInt     = EthToken.wrapEthNum
  implicit lazy val _ETH_s: String => EthToken.DslImpl     = _ETH_d(_)
  implicit lazy val _ETH_b: Double => EthToken.DslImpl     = EthToken.wrapEthNum
  implicit lazy val _ETH_d: BigDecimal => EthToken.DslImpl = EthToken.wrapEthNum

  lazy val CNY                                            = CnyCash.CNY
  lazy val CNJiao                                         = CnyCash.Jiao
  lazy val CNFen                                          = CnyCash.Fen
  lazy val CNFen3                                         = CnyCash.Fen3
  implicit lazy val _CNY_i: Int => CnyCash.DslImplInt     = CnyCash.wrapCnyNum
  implicit lazy val _CNY_s: String => CnyCash.DslImpl     = _CNY_d(_)
  implicit lazy val _CNY_b: Double => CnyCash.DslImpl     = CnyCash.wrapCnyNum
  implicit lazy val _CNY_d: BigDecimal => CnyCash.DslImpl = CnyCash.wrapCnyNum

  lazy val USDT                                              = UsdtToken.USDT
  lazy val USFen3                                            = UsdtToken.USFen3
  implicit lazy val _USDT_i: Int => UsdtToken.DslImplInt     = UsdtToken.wrapUsdtNum
  implicit lazy val _USDT_s: String => UsdtToken.DslImpl     = _USDT_d(_)
  implicit lazy val _USDT_b: Double => UsdtToken.DslImpl     = UsdtToken.wrapUsdtNum
  implicit lazy val _USDT_d: BigDecimal => UsdtToken.DslImpl = UsdtToken.wrapUsdtNum

  lazy val ADA                                             = AdaToken.ADA
  implicit lazy val _ADA_i: Int => AdaToken.DslImpl        = _ADA_d(_)
  implicit lazy val _ADA_s: String => AdaToken.DslImpl     = _ADA_d(_)
  implicit lazy val _ADA_b: Double => AdaToken.DslImpl     = AdaToken.wrapAdaNum
  implicit lazy val _ADA_d: BigDecimal => AdaToken.DslImpl = AdaToken.wrapAdaNum

  lazy val ADEL                                              = AdelToken.ADEL
  implicit lazy val _ADEL_i: Int => AdelToken.DslImpl        = _ADEL_d(_)
  implicit lazy val _ADEL_s: String => AdelToken.DslImpl     = _ADEL_d(_)
  implicit lazy val _ADEL_b: Double => AdelToken.DslImpl     = AdelToken.wrapAdelNum
  implicit lazy val _ADEL_d: BigDecimal => AdelToken.DslImpl = AdelToken.wrapAdelNum

  lazy val AE                                            = AeToken.AE
  implicit lazy val _AE_i: Int => AeToken.DslImpl        = _AE_d(_)
  implicit lazy val _AE_s: String => AeToken.DslImpl     = _AE_d(_)
  implicit lazy val _AE_b: Double => AeToken.DslImpl     = AeToken.wrapAeNum
  implicit lazy val _AE_d: BigDecimal => AeToken.DslImpl = AeToken.wrapAeNum

  lazy val AKRO                                              = AkroToken.AKRO
  implicit lazy val _AKRO_i: Int => AkroToken.DslImpl        = _AKRO_d(_)
  implicit lazy val _AKRO_s: String => AkroToken.DslImpl     = _AKRO_d(_)
  implicit lazy val _AKRO_b: Double => AkroToken.DslImpl     = AkroToken.wrapAkroNum
  implicit lazy val _AKRO_d: BigDecimal => AkroToken.DslImpl = AkroToken.wrapAkroNum

  lazy val ALGO                                              = AlgoToken.ALGO
  implicit lazy val _ALGO_i: Int => AlgoToken.DslImpl        = _ALGO_d(_)
  implicit lazy val _ALGO_s: String => AlgoToken.DslImpl     = _ALGO_d(_)
  implicit lazy val _ALGO_b: Double => AlgoToken.DslImpl     = AlgoToken.wrapAlgoNum
  implicit lazy val _ALGO_d: BigDecimal => AlgoToken.DslImpl = AlgoToken.wrapAlgoNum

  lazy val AXIS                                              = AxisToken.AXIS
  implicit lazy val _AXIS_i: Int => AxisToken.DslImpl        = _AXIS_d(_)
  implicit lazy val _AXIS_s: String => AxisToken.DslImpl     = _AXIS_d(_)
  implicit lazy val _AXIS_b: Double => AxisToken.DslImpl     = AxisToken.wrapAxisNum
  implicit lazy val _AXIS_d: BigDecimal => AxisToken.DslImpl = AxisToken.wrapAxisNum

  lazy val BCH                                             = BchToken.BCH
  implicit lazy val _BCH_i: Int => BchToken.DslImpl        = _BCH_d(_)
  implicit lazy val _BCH_s: String => BchToken.DslImpl     = _BCH_d(_)
  implicit lazy val _BCH_b: Double => BchToken.DslImpl     = BchToken.wrapBchNum
  implicit lazy val _BCH_d: BigDecimal => BchToken.DslImpl = BchToken.wrapBchNum

  lazy val BCHSV                                           = BsvToken.BCHSV
  implicit lazy val _BSV_i: Int => BsvToken.DslImpl        = _BSV_d(_)
  implicit lazy val _BSV_s: String => BsvToken.DslImpl     = _BSV_d(_)
  implicit lazy val _BSV_b: Double => BsvToken.DslImpl     = BsvToken.wrapBsvNum
  implicit lazy val _BSV_d: BigDecimal => BsvToken.DslImpl = BsvToken.wrapBsvNum

  lazy val CREDIT                                                = CreditToken.CREDIT
  implicit lazy val _CREDIT_i: Int => CreditToken.DslImpl        = _CREDIT_d(_)
  implicit lazy val _CREDIT_s: String => CreditToken.DslImpl     = _CREDIT_d(_)
  implicit lazy val _CREDIT_b: Double => CreditToken.DslImpl     = CreditToken.wrapCreditNum
  implicit lazy val _CREDIT_d: BigDecimal => CreditToken.DslImpl = CreditToken.wrapCreditNum

  lazy val CRV                                             = CrvToken.CRV
  implicit lazy val _CRV_i: Int => CrvToken.DslImpl        = _CRV_d(_)
  implicit lazy val _CRV_s: String => CrvToken.DslImpl     = _CRV_d(_)
  implicit lazy val _CRV_b: Double => CrvToken.DslImpl     = CrvToken.wrapCrvNum
  implicit lazy val _CRV_d: BigDecimal => CrvToken.DslImpl = CrvToken.wrapCrvNum

  lazy val CS                                            = CsToken.CS
  implicit lazy val _CS_i: Int => CsToken.DslImpl        = _CS_d(_)
  implicit lazy val _CS_s: String => CsToken.DslImpl     = _CS_d(_)
  implicit lazy val _CS_b: Double => CsToken.DslImpl     = CsToken.wrapCsNum
  implicit lazy val _CS_d: BigDecimal => CsToken.DslImpl = CsToken.wrapCsNum

  lazy val DDD                                             = DddToken.DDD
  implicit lazy val _DDD_i: Int => DddToken.DslImpl        = _DDD_d(_)
  implicit lazy val _DDD_s: String => DddToken.DslImpl     = _DDD_d(_)
  implicit lazy val _DDD_b: Double => DddToken.DslImpl     = DddToken.wrapDddNum
  implicit lazy val _DDD_d: BigDecimal => DddToken.DslImpl = DddToken.wrapDddNum

  lazy val DF                                            = DfToken.DF
  implicit lazy val _DF_i: Int => DfToken.DslImpl        = _DF_d(_)
  implicit lazy val _DF_s: String => DfToken.DslImpl     = _DF_d(_)
  implicit lazy val _DF_b: Double => DfToken.DslImpl     = DfToken.wrapDfNum
  implicit lazy val _DF_d: BigDecimal => DfToken.DslImpl = DfToken.wrapDfNum

  lazy val DKA                                             = DkaToken.DKA
  implicit lazy val _DKA_i: Int => DkaToken.DslImpl        = _DKA_d(_)
  implicit lazy val _DKA_s: String => DkaToken.DslImpl     = _DKA_d(_)
  implicit lazy val _DKA_b: Double => DkaToken.DslImpl     = DkaToken.wrapDkaNum
  implicit lazy val _DKA_d: BigDecimal => DkaToken.DslImpl = DkaToken.wrapDkaNum

  lazy val DOGE                                              = DogeToken.DOGE
  implicit lazy val _DOGE_i: Int => DogeToken.DslImpl        = _DOGE_d(_)
  implicit lazy val _DOGE_s: String => DogeToken.DslImpl     = _DOGE_d(_)
  implicit lazy val _DOGE_b: Double => DogeToken.DslImpl     = DogeToken.wrapDogeNum
  implicit lazy val _DOGE_d: BigDecimal => DogeToken.DslImpl = DogeToken.wrapDogeNum

  lazy val DOT                                             = DotToken.DOT
  implicit lazy val _DOT_i: Int => DotToken.DslImpl        = _DOT_d(_)
  implicit lazy val _DOT_s: String => DotToken.DslImpl     = _DOT_d(_)
  implicit lazy val _DOT_b: Double => DotToken.DslImpl     = DotToken.wrapDotNum
  implicit lazy val _DOT_d: BigDecimal => DotToken.DslImpl = DotToken.wrapDotNum

  lazy val ELF                                             = ElfToken.ELF
  implicit lazy val _ELF_i: Int => ElfToken.DslImpl        = _ELF_d(_)
  implicit lazy val _ELF_s: String => ElfToken.DslImpl     = _ELF_d(_)
  implicit lazy val _ELF_b: Double => ElfToken.DslImpl     = ElfToken.wrapElfNum
  implicit lazy val _ELF_d: BigDecimal => ElfToken.DslImpl = ElfToken.wrapElfNum

  lazy val EOS                                             = EosToken.EOS
  implicit lazy val _EOS_i: Int => EosToken.DslImpl        = _EOS_d(_)
  implicit lazy val _EOS_s: String => EosToken.DslImpl     = _EOS_d(_)
  implicit lazy val _EOS_b: Double => EosToken.DslImpl     = EosToken.wrapEosNum
  implicit lazy val _EOS_d: BigDecimal => EosToken.DslImpl = EosToken.wrapEosNum

  lazy val FIL                                             = FilToken.FIL
  implicit lazy val _FIL_i: Int => FilToken.DslImpl        = _FIL_d(_)
  implicit lazy val _FIL_s: String => FilToken.DslImpl     = _FIL_d(_)
  implicit lazy val _FIL_b: Double => FilToken.DslImpl     = FilToken.wrapFilNum
  implicit lazy val _FIL_d: BigDecimal => FilToken.DslImpl = FilToken.wrapFilNum

  lazy val FIL6                                              = Fil6Token.FIL6
  implicit lazy val _FIL6_i: Int => Fil6Token.DslImpl        = _FIL6_d(_)
  implicit lazy val _FIL6_s: String => Fil6Token.DslImpl     = _FIL6_d(_)
  implicit lazy val _FIL6_b: Double => Fil6Token.DslImpl     = Fil6Token.wrapFil6Num
  implicit lazy val _FIL6_d: BigDecimal => Fil6Token.DslImpl = Fil6Token.wrapFil6Num

  lazy val GARD                                              = GardToken.GARD
  implicit lazy val _GARD_i: Int => GardToken.DslImpl        = _GARD_d(_)
  implicit lazy val _GARD_s: String => GardToken.DslImpl     = _GARD_d(_)
  implicit lazy val _GARD_b: Double => GardToken.DslImpl     = GardToken.wrapGardNum
  implicit lazy val _GARD_d: BigDecimal => GardToken.DslImpl = GardToken.wrapGardNum

  lazy val GT                                            = GtToken.GT
  implicit lazy val _GT_i: Int => GtToken.DslImpl        = _GT_d(_)
  implicit lazy val _GT_s: String => GtToken.DslImpl     = _GT_d(_)
  implicit lazy val _GT_b: Double => GtToken.DslImpl     = GtToken.wrapGtNum
  implicit lazy val _GT_d: BigDecimal => GtToken.DslImpl = GtToken.wrapGtNum

  lazy val INK                                             = InkToken.INK
  implicit lazy val _INK_i: Int => InkToken.DslImpl        = _INK_d(_)
  implicit lazy val _INK_s: String => InkToken.DslImpl     = _INK_d(_)
  implicit lazy val _INK_b: Double => InkToken.DslImpl     = InkToken.wrapInkNum
  implicit lazy val _INK_d: BigDecimal => InkToken.DslImpl = InkToken.wrapInkNum

  lazy val IOTA                                              = IotaToken.IOTA
  implicit lazy val _IOTA_i: Int => IotaToken.DslImpl        = _IOTA_d(_)
  implicit lazy val _IOTA_s: String => IotaToken.DslImpl     = _IOTA_d(_)
  implicit lazy val _IOTA_b: Double => IotaToken.DslImpl     = IotaToken.wrapIotaNum
  implicit lazy val _IOTA_d: BigDecimal => IotaToken.DslImpl = IotaToken.wrapIotaNum

  lazy val IOTX                                              = IotxToken.IOTX
  implicit lazy val _IOTX_i: Int => IotxToken.DslImpl        = _IOTX_d(_)
  implicit lazy val _IOTX_s: String => IotxToken.DslImpl     = _IOTX_d(_)
  implicit lazy val _IOTX_b: Double => IotxToken.DslImpl     = IotxToken.wrapIotxNum
  implicit lazy val _IOTX_d: BigDecimal => IotxToken.DslImpl = IotxToken.wrapIotxNum

  lazy val IRIS                                              = IrisToken.IRIS
  implicit lazy val _IRIS_i: Int => IrisToken.DslImpl        = _IRIS_d(_)
  implicit lazy val _IRIS_s: String => IrisToken.DslImpl     = _IRIS_d(_)
  implicit lazy val _IRIS_b: Double => IrisToken.DslImpl     = IrisToken.wrapIrisNum
  implicit lazy val _IRIS_d: BigDecimal => IrisToken.DslImpl = IrisToken.wrapIrisNum

  lazy val KLAY                                              = KlayToken.KLAY
  implicit lazy val _KLAY_i: Int => KlayToken.DslImpl        = _KLAY_d(_)
  implicit lazy val _KLAY_s: String => KlayToken.DslImpl     = _KLAY_d(_)
  implicit lazy val _KLAY_b: Double => KlayToken.DslImpl     = KlayToken.wrapKlayNum
  implicit lazy val _KLAY_d: BigDecimal => KlayToken.DslImpl = KlayToken.wrapKlayNum

  lazy val KSM                                             = KsmToken.KSM
  implicit lazy val _KSM_i: Int => KsmToken.DslImpl        = _KSM_d(_)
  implicit lazy val _KSM_s: String => KsmToken.DslImpl     = _KSM_d(_)
  implicit lazy val _KSM_b: Double => KsmToken.DslImpl     = KsmToken.wrapKsmNum
  implicit lazy val _KSM_d: BigDecimal => KsmToken.DslImpl = KsmToken.wrapKsmNum

  lazy val LBA                                             = LbaToken.LBA
  implicit lazy val _LBA_i: Int => LbaToken.DslImpl        = _LBA_d(_)
  implicit lazy val _LBA_s: String => LbaToken.DslImpl     = _LBA_d(_)
  implicit lazy val _LBA_b: Double => LbaToken.DslImpl     = LbaToken.wrapLbaNum
  implicit lazy val _LBA_d: BigDecimal => LbaToken.DslImpl = LbaToken.wrapLbaNum

  lazy val LTC                                             = LtcToken.LTC
  implicit lazy val _LTC_i: Int => LtcToken.DslImpl        = _LTC_d(_)
  implicit lazy val _LTC_s: String => LtcToken.DslImpl     = _LTC_d(_)
  implicit lazy val _LTC_b: Double => LtcToken.DslImpl     = LtcToken.wrapLtcNum
  implicit lazy val _LTC_d: BigDecimal => LtcToken.DslImpl = LtcToken.wrapLtcNum

  lazy val MED                                             = MedToken.MED
  implicit lazy val _MED_i: Int => MedToken.DslImpl        = _MED_d(_)
  implicit lazy val _MED_s: String => MedToken.DslImpl     = _MED_d(_)
  implicit lazy val _MED_b: Double => MedToken.DslImpl     = MedToken.wrapMedNum
  implicit lazy val _MED_d: BigDecimal => MedToken.DslImpl = MedToken.wrapMedNum

  lazy val MINI                                              = MiniToken.MINI
  implicit lazy val _MINI_i: Int => MiniToken.DslImpl        = _MINI_d(_)
  implicit lazy val _MINI_s: String => MiniToken.DslImpl     = _MINI_d(_)
  implicit lazy val _MINI_b: Double => MiniToken.DslImpl     = MiniToken.wrapMiniNum
  implicit lazy val _MINI_d: BigDecimal => MiniToken.DslImpl = MiniToken.wrapMiniNum

  lazy val NKN                                             = NknToken.NKN
  implicit lazy val _NKN_i: Int => NknToken.DslImpl        = _NKN_d(_)
  implicit lazy val _NKN_s: String => NknToken.DslImpl     = _NKN_d(_)
  implicit lazy val _NKN_b: Double => NknToken.DslImpl     = NknToken.wrapNknNum
  implicit lazy val _NKN_d: BigDecimal => NknToken.DslImpl = NknToken.wrapNknNum

  lazy val ONT                                             = OntToken.ONT
  implicit lazy val _ONT_i: Int => OntToken.DslImpl        = _ONT_d(_)
  implicit lazy val _ONT_s: String => OntToken.DslImpl     = _ONT_d(_)
  implicit lazy val _ONT_b: Double => OntToken.DslImpl     = OntToken.wrapOntNum
  implicit lazy val _ONT_d: BigDecimal => OntToken.DslImpl = OntToken.wrapOntNum

  lazy val QTUM                                              = QtumToken.QTUM
  implicit lazy val _QTUM_i: Int => QtumToken.DslImpl        = _QTUM_d(_)
  implicit lazy val _QTUM_s: String => QtumToken.DslImpl     = _QTUM_d(_)
  implicit lazy val _QTUM_b: Double => QtumToken.DslImpl     = QtumToken.wrapQtumNum
  implicit lazy val _QTUM_d: BigDecimal => QtumToken.DslImpl = QtumToken.wrapQtumNum

  lazy val RDN                                             = RdnToken.RDN
  implicit lazy val _RDN_i: Int => RdnToken.DslImpl        = _RDN_d(_)
  implicit lazy val _RDN_s: String => RdnToken.DslImpl     = _RDN_d(_)
  implicit lazy val _RDN_b: Double => RdnToken.DslImpl     = RdnToken.wrapRdnNum
  implicit lazy val _RDN_d: BigDecimal => RdnToken.DslImpl = RdnToken.wrapRdnNum

  lazy val RLC                                             = RlcToken.RLC
  implicit lazy val _RLC_i: Int => RlcToken.DslImpl        = _RLC_d(_)
  implicit lazy val _RLC_s: String => RlcToken.DslImpl     = _RLC_d(_)
  implicit lazy val _RLC_b: Double => RlcToken.DslImpl     = RlcToken.wrapRlcNum
  implicit lazy val _RLC_d: BigDecimal => RlcToken.DslImpl = RlcToken.wrapRlcNum

  lazy val SALT                                              = SaltToken.SALT
  implicit lazy val _SALT_i: Int => SaltToken.DslImpl        = _SALT_d(_)
  implicit lazy val _SALT_s: String => SaltToken.DslImpl     = _SALT_d(_)
  implicit lazy val _SALT_b: Double => SaltToken.DslImpl     = SaltToken.wrapSaltNum
  implicit lazy val _SALT_d: BigDecimal => SaltToken.DslImpl = SaltToken.wrapSaltNum

  lazy val SRM                                             = SrmToken.SRM
  implicit lazy val _SRM_i: Int => SrmToken.DslImpl        = _SRM_d(_)
  implicit lazy val _SRM_s: String => SrmToken.DslImpl     = _SRM_d(_)
  implicit lazy val _SRM_b: Double => SrmToken.DslImpl     = SrmToken.wrapSrmNum
  implicit lazy val _SRM_d: BigDecimal => SrmToken.DslImpl = SrmToken.wrapSrmNum

  lazy val SUSHI                                               = SushiToken.SUSHI
  implicit lazy val _SUSHI_i: Int => SushiToken.DslImpl        = _SUSHI_d(_)
  implicit lazy val _SUSHI_s: String => SushiToken.DslImpl     = _SUSHI_d(_)
  implicit lazy val _SUSHI_b: Double => SushiToken.DslImpl     = SushiToken.wrapSushiNum
  implicit lazy val _SUSHI_d: BigDecimal => SushiToken.DslImpl = SushiToken.wrapSushiNum

  lazy val TSL                                             = TslToken.TSL
  implicit lazy val _TSL_i: Int => TslToken.DslImpl        = _TSL_d(_)
  implicit lazy val _TSL_s: String => TslToken.DslImpl     = _TSL_d(_)
  implicit lazy val _TSL_b: Double => TslToken.DslImpl     = TslToken.wrapTslNum
  implicit lazy val _TSL_d: BigDecimal => TslToken.DslImpl = TslToken.wrapTslNum

  lazy val UMA                                             = UmaToken.UMA
  implicit lazy val _UMA_i: Int => UmaToken.DslImpl        = _UMA_d(_)
  implicit lazy val _UMA_s: String => UmaToken.DslImpl     = _UMA_d(_)
  implicit lazy val _UMA_b: Double => UmaToken.DslImpl     = UmaToken.wrapUmaNum
  implicit lazy val _UMA_d: BigDecimal => UmaToken.DslImpl = UmaToken.wrapUmaNum

  lazy val WNXM                                              = WnxmToken.WNXM
  implicit lazy val _WNXM_i: Int => WnxmToken.DslImpl        = _WNXM_d(_)
  implicit lazy val _WNXM_s: String => WnxmToken.DslImpl     = _WNXM_d(_)
  implicit lazy val _WNXM_b: Double => WnxmToken.DslImpl     = WnxmToken.wrapWnxmNum
  implicit lazy val _WNXM_d: BigDecimal => WnxmToken.DslImpl = WnxmToken.wrapWnxmNum

  lazy val XRP                                             = XrpToken.XRP
  implicit lazy val _XRP_i: Int => XrpToken.DslImpl        = _XRP_d(_)
  implicit lazy val _XRP_s: String => XrpToken.DslImpl     = _XRP_d(_)
  implicit lazy val _XRP_b: Double => XrpToken.DslImpl     = XrpToken.wrapXrpNum
  implicit lazy val _XRP_d: BigDecimal => XrpToken.DslImpl = XrpToken.wrapXrpNum

  lazy val YAMV2                                              = Yam2Token.YAMV2
  implicit lazy val _YAMV2_i: Int => Yam2Token.DslImpl        = _YAMV2_d(_)
  implicit lazy val _YAMV2_s: String => Yam2Token.DslImpl     = _YAMV2_d(_)
  implicit lazy val _YAMV2_b: Double => Yam2Token.DslImpl     = Yam2Token.wrapYAMv2Num
  implicit lazy val _YAMV2_d: BigDecimal => Yam2Token.DslImpl = Yam2Token.wrapYAMv2Num

  lazy val YFII                                              = YfiiToken.YFII
  implicit lazy val _YFII_i: Int => YfiiToken.DslImpl        = _YFII_d(_)
  implicit lazy val _YFII_s: String => YfiiToken.DslImpl     = _YFII_d(_)
  implicit lazy val _YFII_b: Double => YfiiToken.DslImpl     = YfiiToken.wrapYfiiNum
  implicit lazy val _YFII_d: BigDecimal => YfiiToken.DslImpl = YfiiToken.wrapYfiiNum

  lazy val YFI                                             = YfiToken.YFI
  implicit lazy val _YFI_i: Int => YfiToken.DslImpl        = _YFI_d(_)
  implicit lazy val _YFI_s: String => YfiToken.DslImpl     = _YFI_d(_)
  implicit lazy val _YFI_b: Double => YfiToken.DslImpl     = YfiToken.wrapYfiNum
  implicit lazy val _YFI_d: BigDecimal => YfiToken.DslImpl = YfiToken.wrapYfiNum

  lazy val YFV                                             = YfvToken.YFV
  implicit lazy val _YFV_i: Int => YfvToken.DslImpl        = _YFV_d(_)
  implicit lazy val _YFV_s: String => YfvToken.DslImpl     = _YFV_d(_)
  implicit lazy val _YFV_b: Double => YfvToken.DslImpl     = YfvToken.wrapYfvNum
  implicit lazy val _YFV_d: BigDecimal => YfvToken.DslImpl = YfvToken.wrapYfvNum

  lazy val ZEC                                               = ZCashToken.ZEC
  implicit lazy val _ZEC_i: Int => ZCashToken.DslImpl        = _ZEC_d(_)
  implicit lazy val _ZEC_s: String => ZCashToken.DslImpl     = _ZEC_d(_)
  implicit lazy val _ZEC_b: Double => ZCashToken.DslImpl     = ZCashToken.wrapZecNum
  implicit lazy val _ZEC_d: BigDecimal => ZCashToken.DslImpl = ZCashToken.wrapZecNum

  lazy val ZRX                                             = ZrxToken.ZRX
  implicit lazy val _ZRX_i: Int => ZrxToken.DslImpl        = _ZRX_d(_)
  implicit lazy val _ZRX_s: String => ZrxToken.DslImpl     = _ZRX_d(_)
  implicit lazy val _ZRX_b: Double => ZrxToken.DslImpl     = ZrxToken.wrapZrxNum
  implicit lazy val _ZRX_d: BigDecimal => ZrxToken.DslImpl = ZrxToken.wrapZrxNum

  object Serializer extends (AbsCoinGroup#AbsCoin => (BigDecimal, String)) {

    def apply(coin: AbsCoinGroup#AbsCoin): (BigDecimal, String) = {
      val std = coin.std
      (std.value, std.unit.name)
    }

    //@throws[RuntimeException]
    def unapply(coin: (BigDecimal, String)): Option[AbsCoinGroup#AbsCoin] = Option(coin._2 match {
      case CNY.name    => coin._1 CNY
      case USDT.name   => coin._1 USDT
      case BTC.name    => coin._1 BTC
      case ETH.name    => coin._1 ETH
      case ADA.name    => coin._1 ADA
      case ADEL.name   => coin._1 ADEL
      case AE.name     => coin._1 AE
      case ALGO.name   => coin._1 ALGO
      case AKRO.name   => coin._1 AKRO
      case AXIS.name   => coin._1 AXIS
      case BCH.name    => coin._1 BCH
      case BCHSV.name  => coin._1 BCHSV
      case CREDIT.name => coin._1 CREDIT
      case CRV.name    => coin._1 CRV
      case CS.name     => coin._1 CS
      case DDD.name    => coin._1 DDD
      case DF.name     => coin._1 DF
      case DKA.name    => coin._1 DKA
      case DOGE.name   => coin._1 DOGE
      case DOT.name    => coin._1 DOT
      case ELF.name    => coin._1 ELF
      case EOS.name    => coin._1 EOS
      case FIL.name    => coin._1 FIL
      case FIL6.name   => coin._1 FIL6
      case GARD.name   => coin._1 GARD
      case GT.name     => coin._1 GT
      case INK.name    => coin._1 INK
      case IOTA.name   => coin._1 IOTA
      case IOTX.name   => coin._1 IOTX
      case IRIS.name   => coin._1 IRIS
      case KLAY.name   => coin._1 KLAY
      case KSM.name    => coin._1 KSM
      case LBA.name    => coin._1 LBA
      case LTC.name    => coin._1 LTC
      case MED.name    => coin._1 MED
      case MINI.name   => coin._1 MINI
      case NKN.name    => coin._1 NKN
      case ONT.name    => coin._1 ONT
      case QTUM.name   => coin._1 QTUM
      case RDN.name    => coin._1 RDN
      case RLC.name    => coin._1 RLC
      case SALT.name   => coin._1 SALT
      case SRM.name    => coin._1 SRM
      case SUSHI.name  => coin._1 SUSHI
      case TSL.name    => coin._1 TSL
      case UMA.name    => coin._1 UMA
      case WNXM.name   => coin._1 WNXM
      case XRP.name    => coin._1 XRP
      case YAMV2.name  => coin._1 YAMV2
      case YFI.name    => coin._1 YFI
      case YFII.name   => coin._1 YFII
      case YFV.name    => coin._1 YFV
      case ZEC.name    => coin._1 ZEC
      case ZRX.name    => coin._1 ZRX
      case _           => null // 交易所有很多币种，简单地抛出异常是不行的。 throw new RuntimeException(s"不存在指定的币种，或不是[标准]单位:$coin".tag)
    })
  }

  implicit class Serializable(coin: AbsCoinGroup#AbsCoin) {
    def serialize: (BigDecimal, String) = Serializer(coin)
  }

  implicit class Deserializable(coin: (BigDecimal, String)) {

    //@throws[RuntimeException]
    def desrl: Option[AbsCoinGroup#AbsCoin] = (coin /*: @unchecked*/ ) match {
      case Serializer(tuple) => Some(tuple)
      case _                 => None
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

  @inline implicit def string2BigInt(count: String): BigInt         = BigInt(count)
  @inline implicit def bigDecimal2BigInt(count: BigDecimal): BigInt = count.toBigInt()
  @inline implicit def string2BigDecimal(count: String): BigDecimal = BigDecimal(count)
  @inline implicit def bigInt2BigDecimal(count: BigInt): BigDecimal = BigDecimal(count)
  // NOT needed. 否则`BtcToken`中的`10.pow(8)`有多个隐式转换的错误，但
  // 如果只去掉`int2BigDecimal()`会使原本应用`int`的转而应用`double2BigDecimal()`。
//  @inline implicit def double2BigDecimal(count: Double): BigDecimal = BigDecimal(count)
//  @inline implicit def int2BigDecimal(count: Int): BigDecimal       = BigDecimal(count)

  lazy val ZERO: BigDecimal = 0
  lazy val ONE: BigDecimal  = 1

  lazy val ZERO_CNY = 0.CNY
  lazy val ONE_CNY  = 1.CNY

  lazy val ZERO_USDT = 0.USDT
}

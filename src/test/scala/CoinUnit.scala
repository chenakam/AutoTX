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

import hobby.chenai.nakam.txdsl.coin.BtcGroup._
import hobby.chenai.nakam.txdsl.coin.CnyGroup
import hobby.chenai.nakam.txdsl.coin.CnyGroup._
import hobby.chenai.nakam.txdsl.coin.EthGroup._
import hobby.chenai.nakam.txdsl.core.DSL._
import hobby.chenai.nakam.txdsl.core.exch.{AbsExchange, YUNBI}

import scala.language.postfixOps

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 08/06/2017
  */
object CoinUnit {
  def main(args: Array[String]): Unit = {
  }

  implicit val exchange: AbsExchange = YUNBI
  YUNBI.updateCashPricingRate(BTC, 40000 CNY)
  YUNBI.updateCashPricingRate(ETH, 3000 CNY)

  \("(0 BTC) to BTC: " + ((0 BTC) to BTC))
  \("(0 BTC) to SAT: " + ((0 BTC) to SAT))
  \("(0 BTC) to CNY: " + ((0 BTC) to CNY))
  \("(0 BTC) to FEN: " + ((0 BTC) to Fen))
  \("(0 BTC) to ETH: " + ((0 BTC) to ETH))

  ln

  \("(1 BTC) to BTC: " + ((1 BTC) to BTC))
  \("(1 BTC) to SAT: " + ((1 BTC) to SAT))
  \("(1 BTC) to CNY: " + ((1 BTC) to CNY))
  \("(1 BTC) to FEN: " + ((1 BTC) to Fen))
  \("(1 BTC) to ETH: " + ((1 BTC) to ETH))

  ln

  \("(0.1 BTC) to BTC: " + ((0.1 BTC) to BTC))
  \("(0.1 BTC) to SAT: " + ((0.1 BTC) to SAT))
  \("(0.1 BTC) to CNY: " + ((0.1 BTC) to CNY))
  \("(0.1 BTC) to FEN: " + ((0.1 BTC) to Fen))
  \("(0.1 BTC) to ETH: " + ((0.1 BTC) to ETH))

  ln

  \("5.44 JIAO: " + (5.44 Jiao))
  \("5.45 JIAO: " + (5.45 Jiao))
  \("1.494 FEN: " + (1.494 Fen))
  \("1.495 FEN: " + (1.495 Fen))
  \("1.44 FEN: " + (1.44 Fen))
  \("1.45 FEN: " + (1.45 Fen))
  \("1.4 FEN: " + (1.4 Fen))
  \("1.5 FEN: " + (1.5 Fen))

  ln

  \("(1.12 CNY) > (112 FEN): " + ((1.12 CNY) > (112 Fen)))
  \("(1.12 CNY) == (112 FEN): " + ((1.12 CNY) == (112 Fen)))
  \("(1.12 CNY) == (112.1 FEN): " + ((1.12 CNY) == (112.1 Fen)))
  \("(1.12 CNY) < (112.1 FEN): " + ((1.12 CNY) < (112.1 Fen)))

  ln

  \("(980 FEN) to (2 SAT).unit: " + ((980 Fen) to (2 SAT).unit))
  \("(980 FEN) to (2 SAT).std.unit: " + ((980 Fen) to (2 SAT).std.unit))
  \("(980 FEN) to JIAO: " + ((980 Fen) to Jiao))
  \("(5.4 JIAO) to ETH: " + ((5.4 Jiao) to ETH))
  \("(5.4 JIAO) to ETH to CNY: " + ((5.4 Jiao) to ETH to CNY))
  \("(5.45 JIAO) to ETH to CNY to BTC to JIAO: " + ((5.45 Jiao) to ETH to CNY to BTC to Jiao))
  // SAT 的精度太高，转换次数过多，会有损失。
  \("(5.51 SAT) to ETH to BTC to FEN to SAT: " + ((5.51 SAT) to ETH to BTC to Fen to SAT))
  \("(5.51 SAT) to ETH: " + ((5.51 SAT) to ETH))

  ln

  // SAT 的精度太高，会有损失
  //  BTCC.updateCashRate(BTC, 20000000)
  //  BTCC.updateCashRate(ETH, 165000)
  //
  //  implicit val exchangeBtcc = BTCC

  \("(5.44 JIAO) to ETH to CNY to BTC to JIAO: " + ((5.44 Jiao) to ETH to CNY to BTC to Jiao))
  val jiao: CnyGroup.COIN = CNY.^((5.4506789 Jiao) to ETH to CNY to BTC to Jiao)
  //  \("(5.45 JIAO) to ETH to CNY to BTC to JIAO: " + jiao.value(CNY))
  \("(5.45 JIAO) to ETH to CNY to BTC to JIAO to FEN_3: " + (jiao to Fen_3))
  \("(5.49 SAT) to ETH to BTC to FEN to SAT: " + ((5.49 SAT) to ETH to BTC to Fen to SAT))
  \("(5.51 SAT) to ETH to BTC to FEN to SAT: " + ((5.51 SAT) to ETH to BTC to Fen to SAT))
  \("(5.51 SAT) to ETH: " + ((5.51 SAT) to ETH))

  ln

  \("(1 CNY) == (10 FEN): " + ((1 CNY) == (10 Fen)))
  \("(1 CNY) > (10 FEN): " + ((1 CNY) > (10 Fen)))
  \("(1 CNY) == (10 JIAO): " + ((1 CNY) == (10 Jiao)))
  \("(1 CNY) < (10 JIAO): " + ((1 CNY) < (10 Jiao)))
  \("(1 BTC) == (1 CNY): " + ((1 BTC) == (1 CNY)))
  \("(0.00050 BTC) == (50000 SAT): " + ((0.00050 BTC) == (50000 SAT)))
  \("(1.12 CNY) == (112 FEN): " + ((1.12 CNY) == (112 Fen)))
  \("(1.12 CNY) >= (112 FEN): " + ((1.12 CNY) >= (112 Fen)))
  \("(1.12 CNY) > (112 FEN): " + ((1.12 CNY) > (112 Fen)))
  \("(1.12 CNY) != (112 FEN): " + ((1.12 CNY) != (112 Fen)))
  \("(1.12 CNY) < (112.1 FEN): " + ((1.12 CNY) < (112.1 Fen)))

  ln()

  \("(1 SAT) to FEN_3: " + ((1 SAT) to Fen_3))
  \("(1 SAT) to FEN: " + ((1 SAT) to Fen))
  \("(1 SAT) to CNY: " + ((1 SAT) to CNY))

  \(SAT)
  \(BTC)
  \(CNY)
  \(Fen)
  \(Fen_3)
}

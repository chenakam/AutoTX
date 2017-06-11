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

import hobby.chenai.nakam.autotx.core.DSL._
import hobby.chenai.nakam.autotx.core.coin.BtcGroup._
import hobby.chenai.nakam.autotx.core.coin.CnyGroup._
import hobby.chenai.nakam.autotx.core.coin.EthGroup._
import hobby.chenai.nakam.autotx.core.exch.YunBiZone.YUNBI
/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 08/06/2017
  */
object CoinUnit {
  def main(args: Array[String]): Unit = {
  }

  YUNBI.updateCashPricingRate(BTC, 17000)
  YUNBI.updateCashPricingRate(ETH, 1650)

  \("(0 BTC) to BTC: " + ((0 BTC) to BTC))
  \("(0 BTC) to CONG: " + ((0 BTC) to CONG))
  \("(0 BTC) to CNY: " + ((0 BTC) to CNY))
  \("(0 BTC) to FEN: " + ((0 BTC) to FEN))
  \("(0 BTC) to ETH: " + ((0 BTC) to ETH))

  ln

  \("(1 BTC) to BTC: " + ((1 BTC) to BTC))
  \("(1 BTC) to CONG: " + ((1 BTC) to CONG))
  \("(1 BTC) to CNY: " + ((1 BTC) to CNY))
  \("(1 BTC) to FEN: " + ((1 BTC) to FEN))
  \("(1 BTC) to ETH: " + ((1 BTC) to ETH))

  ln

  \("(0.1 BTC) to BTC: " + ((0.1 BTC) to BTC))
  \("(0.1 BTC) to CONG: " + ((0.1 BTC) to CONG))
  \("(0.1 BTC) to CNY: " + ((0.1 BTC) to CNY))
  \("(0.1 BTC) to FEN: " + ((0.1 BTC) to FEN))
  \("(0.1 BTC) to ETH: " + ((0.1 BTC) to ETH))

  ln

  \("5.44 JIAO: " + (5.44 JIAO))
  \("5.45 JIAO: " + (5.45 JIAO))
  \("1.494 FEN: " + (1.494 FEN))
  \("1.495 FEN: " + (1.495 FEN))
  \("1.44 FEN: " + (1.44 FEN))
  \("1.45 FEN: " + (1.45 FEN))
  \("1.4 FEN: " + (1.4 FEN))
  \("1.5 FEN: " + (1.5 FEN))

  ln

  \("(1.12 CNY) > (112 FEN): " + ((1.12 CNY) > (112 FEN)))
  \("(1.12 CNY) == (112 FEN): " + ((1.12 CNY) == (112 FEN)))
  \("(1.12 CNY) == (112.1 FEN): " + ((1.12 CNY) == (112.1 FEN)))
  \("(1.12 CNY) < (112.1 FEN): " + ((1.12 CNY) < (112.1 FEN)))

  ln

  \("(980 FEN) to (2 CONG).unit: " + ((980 FEN) to (2 CONG).unit))
  \("(980 FEN) to (2 CONG).std.unit: " + ((980 FEN) to (2 CONG).std.unit))
  \("(980 FEN) to JIAO: " + ((980 FEN) to JIAO))
  \("(5.4 JIAO) to ETH: " + ((5.4 JIAO) to ETH))
  \("(5.4 JIAO) to ETH to CNY: " + ((5.4 JIAO) to ETH to CNY))
  \("(5.45 JIAO) to ETH to CNY to BTC to JIAO: " + ((5.45 JIAO) to ETH to CNY to BTC to JIAO))
  // CONG 的精度太高，转换次数过多，会有损失。
  \("(5.51 CONG) to ETH to BTC to FEN to CONG: " + ((5.51 CONG) to ETH to BTC to FEN to CONG))
  \("(5.51 CONG) to ETH: " + ((5.51 CONG) to ETH))

  ln

  // CONG 的精度太高，会有损失
  //  BTCC.updateCashRate(BTC, 20000000)
  //  BTCC.updateCashRate(ETH, 165000)
  //
  //  implicit val exchangeBtcc = BTCC

  \("(5.44 JIAO) to ETH to CNY to BTC to JIAO: " + ((5.44 JIAO) to ETH to CNY to BTC to JIAO))
  val jiao = (5.4506789 JIAO) to ETH to CNY to BTC to JIAO
  \("(5.45 JIAO) to ETH to CNY to BTC to JIAO: " + jiao.value(CNY))
  \("(5.45 JIAO) to ETH to CNY to BTC to JIAO to FEN_3: " + (jiao to FEN_3))
  \("(5.49 CONG) to ETH to BTC to FEN to CONG: " + ((5.49 CONG) to ETH to BTC to FEN to CONG))
  \("(5.51 CONG) to ETH to BTC to FEN to CONG: " + ((5.51 CONG) to ETH to BTC to FEN to CONG))
  \("(5.51 CONG) to ETH: " + ((5.51 CONG) to ETH))

  ln

  \("(1 CNY) == (10 FEN): " + ((1 CNY) == (10 FEN)))
  \("(1 CNY) > (10 FEN): " + ((1 CNY) > (10 FEN)))
  \("(1 CNY) == (10 JIAO): " + ((1 CNY) == (10 JIAO)))
  \("(1 CNY) < (10 JIAO): " + ((1 CNY) < (10 JIAO)))
  \("(1 BTC) == (1 CNY): " + ((1 BTC) == (1 CNY)))
  \("(0.00050 BTC) == (50000 CONG): " + ((0.00050 BTC) == (50000 CONG)))
  \("(1.12 CNY) == (112 FEN): " + ((1.12 CNY) == (112 FEN)))
  \("(1.12 CNY) >= (112 FEN): " + ((1.12 CNY) >= (112 FEN)))
  \("(1.12 CNY) > (112 FEN): " + ((1.12 CNY) > (112 FEN)))
  \("(1.12 CNY) != (112 FEN): " + ((1.12 CNY) != (112 FEN)))
  \("(1.12 CNY) < (112.1 FEN): " + ((1.12 CNY) < (112.1 FEN)))

  ln()

  \("(1 CONG) to FEN_3: " + ((1 CONG) to FEN_3))
  \("(1 CONG) to FEN: " + ((1 CONG) to FEN))
  \("(1 CONG) to CNY: " + ((1 CONG) to CNY))

  \(CONG)
  \(BTC)
  \(CNY)
  \(FEN)
  \(FEN_3)
}

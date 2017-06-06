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
import hobby.chenai.nakam.autotx.core.FeeSrc
import hobby.chenai.nakam.autotx.core.coin.BtcZone._
import hobby.chenai.nakam.autotx.core.coin.CnyZone._
import hobby.chenai.nakam.autotx.core.coin.EthZone._
import hobby.chenai.nakam.autotx.core.coin.{AbsCoinZone, BtcZone, CnyZone}
import hobby.chenai.nakam.autotx.core.exch.YunBiZone._

import scala.collection.immutable.List

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 28/05/2017
  */
object Syntax {
  def main(args: Array[String]): scala.Unit = {
    // ...
  }

  val f0: (Int, Int) => Int = _ + _
  val f1 = (_: Int) + (_: Int)
  val f2: (Int, Int) => Int = (x: Int, y: Int) => x + y
  val f3 = (x: Int, y: Int) => x + y

  //  buy 1000 BTC from YUNBI

  def \(x: Any) = println(x)

  def ln() = \("")

  YUNBI.updateCashRate(BTC, 17000)
  YUNBI.updateCashRate(ETH, 1650)

  \(CONG == BTC)
  \(BTC == BTC)
  \("BTC equals CNY: " + (CONG equals FEN))
  \(BTC.isInstanceOf[BtcZone.Token])
  \(BTC.getClass eq CONG.getClass)
  \(BTC.getClass eq JIAO.getClass)
  \(BTC.getClass + ", " + JIAO.getClass)
  \(CONG)
  \(BTC)
  \(CONG * 1000 + (2 BTC))
  \((10000 CONG) + (1000 CONG))
  \(1 BTC)
  \("CONG == (1 CONG)? " + (CONG == (1 CONG)))
  \(JIAO)
  \(JIAO == (1 JIAO))
  \("JIAO canEqual CNY: " + (JIAO canEqual CNY))

  //  println(new Token(0).isInstanceOf[BtcZone.BTC])
  matchText(BTC)
  matchText(1 BTC)
  matchText(10 BTC)
  matchText(2 CNY)


  def matchText(token: AbsCoinZone#AbsCoin) = token match {
    case BTC => println(token + " matches BTC object")
    case _: BtcZone.COIN => println(token + " matches BtcZone.COIN type")
    //    case _: BtcZone.Token => println(token + " matches BtcZone.Token type")
    case _: AbsCoinZone#AbsCoin => println(token + " matches AbsTokenZone#AbsCoin type")
    case _ => println(token + "matches nothing")
  }

  \(CNY.getClass == JIAO.getClass)
  \((1 CNY).getClass == JIAO.getClass)
  \(BTC.getClass == CNY.getClass)

  //  \((1 BTC).type == CNY.type)

  \("CNY.isInstanceOf[CnyZone.RMB]:" + CNY.isInstanceOf[CnyZone.RMB])
  \("CNY.isInstanceOf[AbsCoinZone#AbsCash]:" + CNY.isInstanceOf[AbsCoinZone#AbsCash])

  //  require(CNY.getClass == (1 JIAO).getClass, s"${CNY unitName}不支持${JIAO.unitName}")

  \(CNY.getClass + ", class: " + JIAO.getClass)

  type CASH = CnyZone.COIN

  def typeMatch(any: Any) = any match {
    case _: CASH => \ _
    case _ => \("_")
  }

  \("typeMatch(CNY):" + typeMatch(CNY))
  \("typeMatch(JIAO):" + typeMatch(JIAO))
  \("typeMatch(BTC):" + typeMatch(BTC))

  /*
    abstract class Cat[-T, +U, -X, +Y, A] {
      def meow[W](volume: T, listener: Cat[U, T, Y, Cat[U, T, Cat[T,
        Cat[A, U, Cat[U, T, A, A, A], Y, A], X, Y, A], X, A], A])

      : Cat[T, U,
        Cat[Cat[T, U, X, Y, A], Cat[U, T, Y, X, A], U, T, A],
        Cat[T, U, X, Y, A],
        Cat[A, A, A, A, A]]
    }*/

  class A[+T]

  class B[+T] extends A[T]

  class C[T] extends B[T]

  class X

  class Y extends X

  class Z extends Y

  /*
    val aaxx: A[X] = new A[X]
    val aaxy: A[X] = new A[Y]
    val abxx: A[X] = new B[X]
    val abxy: A[X] = new B[Y]
    val aayx: A[Y] = new A[X] // 报错
    val aayy: A[Y] = new A[Y]
    val abyx: A[Y] = new B[X] // 报错
    val abyy: A[Y] = new B[Y]

    val baxx: B[X] = new A[X] // 报错
    val baxy: B[X] = new A[Y] // 报错
    val bayx: B[Y] = new A[X] // 报错
    val bayy: B[Y] = new A[Y] // 报错
    val bbxx: B[X] = new B[X]
    val bbxy: B[X] = new B[Y] // 报错
    val bbyx: B[Y] = new B[X]
    val bbyy: B[Y] = new B[Y]
  */
  /*
    val arr: Array[Any] = Array(1, 2, 3, 4, "")
    //  val arrABY: Array[A[B[Y]]] = Array(new A[B[Y]], new B[B[Y]], new A[B[X]], new B[B[X]], new B[A[Y]])
    val arrAAX: Array[A[A[Y]]] = Array(new A[B[Y]], new B[B[Y]], new A[B[Y]], new B[B[X]])
    val arrABCY: Array[A[B[C[Y]]]] = Array(new A[B[C[Y]]], new B[B[C[Y]]], new B[B[A[X]]])
    val arrNew: Array[A[B[C[Y]]]] = Array[A[B[C[Y]]]]()
    val listNew: List[A[B[C[Y]]]] = List[B[B[C[Y]]]]()
    val arrNew1: Array[A[B[A[B[Y]]]]] = Array[A[B[A[B[Y]]]]]()
    val listNew1: List[A[B[A[B[Y]]]]] = List[A[C[A[A[X]]]]]()

    val seq: Seq[A[X]] = List(new A[Y], new B[Y])*/


  val a = "a"
  val b = List(1, 2, 3, 4, "a")


  \(s"$a[${b.mkString(", ")}]")

  val tokens = Array[AbsCoinZone#AbsCoin](BTC, CONG, CNY, JIAO, FEN, ETH)

  \(CONG == BTC)
  \(Array(CONG).contains(BTC))
  \(tokens.groupBy(_.std.unit).keySet.mkString("groupBy:[", ", ", "]"))

  \((for (t <- tokens) yield (t.std unit).asInstanceOf[AbsCoinZone#AbsCoin]).distinct.mkString("yield:[", ", ", "]"))

  ln()
  ln


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


  ~>: buy (10 BTC) on YUNBI use (160000 CNY) ~~=

  ~>: sale (10 BTC) on YUNBI use (160000 CNY) ~~=

  ~>: cancel (10 BTC) on YUNBI use (160000 CNY) ~~=


  \(FeeSrc.CNY.id + FeeSrc.CNY.toString())
}

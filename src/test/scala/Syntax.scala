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

import hobby.chenai.nakam.txdsl.coin.BtcGroup.{SAT, _}
import hobby.chenai.nakam.txdsl.coin.CnyGroup._
import hobby.chenai.nakam.txdsl.coin.EthGroup._
import hobby.chenai.nakam.txdsl.core.DSL._
import hobby.chenai.nakam.txdsl.core.Fee
import hobby.chenai.nakam.txdsl.core.coin._
import Syntax.{A, ABCD}
import hobby.chenai.nakam.txdsl.coin.{BtcGroup, CnyGroup, EthGroup}
import hobby.chenai.nakam.txdsl.core.exch.{AbsExchange, YUNBI}

import scala.language.{implicitConversions, postfixOps}

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

  implicit val exchange: AbsExchange = YUNBI
  YUNBI.updateCashPricingRate(BTC, 40000 CNY)
  YUNBI.updateCashPricingRate(ETH, 3000 CNY)

  \(SAT == BTC)
  \(BTC == BTC)
  \("BTC equals CNY: " + (SAT equals Fen))
  \(BTC.isInstanceOf[BtcGroup.Token])
  \(BTC.getClass eq SAT.getClass)
  \(BTC.getClass eq Jiao.getClass)
  \(BTC.getClass + ", " + Jiao.getClass)
  \(SAT)
  \(BTC)
  \(SAT * 1000 + (2 BTC))
  \((10000 SAT) + (1000 SAT))
  \(1 BTC)
  \("SAT == (1 SAT)? " + (SAT == (1 SAT)))
  \(Jiao)
  \(Jiao == (1 Jiao))
  \("JIAO canEqual CNY: " + (Jiao canEqual CNY))

  //  println(new Token(0).isInstanceOf[BtcZone.BTC])
  matchText(BTC)
  matchText(1 BTC)
  matchText(10 BTC)
  matchText(2 CNY)


  def matchText(token: AbsCoinGroup#AbsCoin) = token match {
    case BTC => println(token + " matches BTC object")
    case _: BtcGroup.COIN => println(token + " matches BtcZone.COIN type")
    //case _: BtcZone.Token => println(token + " matches BtcZone.Token type")
    case _: AbsCoinGroup#AbsCoin => println(token + " matches AbsTokenZone#AbsCoin type")
    case _ => println(token + "matches nothing")
  }

  \("CNY.isInstanceOf[CnyZone.RMB]:" + CNY.isInstanceOf[CnyGroup.RMB])
  \("CNY.isInstanceOf[AbsCoinZone#AbsCoin]:" + CNY.isInstanceOf[AbsCashGroup#AbsCoin])

  //  require(CNY.getClass == (1 JIAO).getClass, s"${CNY unitName}不支持${JIAO.unitName}")

  \(CNY.getClass + ", class: " + Jiao.getClass)

  type CASH = CnyGroup.COIN

  def typeMatch(any: Any) = any match {
    case _: CASH => \ _
    case _ => \("_")
  }

  \("typeMatch(CNY):" + typeMatch(CNY))
  \("typeMatch(JIAO):" + typeMatch(Jiao))
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

  class A[+T] {
  }

  class B[+T] extends A[T]

  class C[T] extends B[T] {
    def method[N <: Y](t: T, n: N): N = n
  }

  class D[T] extends C[T]

  class E extends D

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

  def test[T: D, M >: T, N <: Y](t: T, m: M, n: N): N = implicitly[C[T]].method(t, n)

  def meow[H <: C[X], I >: H, J >: H <: Any, K >: H <: I](): Unit = {}


  object EthFee extends Fee(EthGroup, CnyGroup) {
    import feeGroup._
    lazy val BUY = new Rule(1 CNY, 0.0001, 1 Jiao)
  }

  ln()
  ln()

  \("buy.costs(1 ETH): " + EthFee.BUY.costs(1 ETH))


  def matchIf(x: Any) = x match {
    case _ if x.isInstanceOf[B[_]] => \("x isInstanceOf B")
    case 10 => \("=> 10")
    case i: Int if i == 10 => \("x == 10")
    case s@_ if s == "string" => \("x == string")
    case a => \(a)
  }

  matchIf(new B[X])
  matchIf(10)
  matchIf("string")
  matchIf('c')

  ln()
  ln()

  ~>: buy (10000 SAT) on YUNBI use (1 CNY) ~>=

  ~>: buy (98 BTC) on YUNBI ~>=

  ~>: buy (98 BTC) ~>=

  \((12.34567890 CNY) formatted())
  \((12.34567890987 Fen_3).formatted(fixedFracDigits = 25)(null))
  \((1234020000 Fen_3) formatted 25)
  \((12345678900000023L SAT) to BTC formatted 25)
  \((23L SAT).formatted(25, 4)(SAT.formatter))


  case class O(var s: String, var i: Int) {
    def test: String = s
  }

  O("", 0) match {
    case s O 5 => s
    case _ => ""
  }

  trait ABCD {
    protected implicit def ^(s: String): O = ???
  }
}

class BCDE extends A with ABCD {
  "" test
}

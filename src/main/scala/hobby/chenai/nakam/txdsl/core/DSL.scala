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

package hobby.chenai.nakam.txdsl.core

import hobby.chenai.nakam.txdsl.core.coin.{AbsCashGroup, AbsCoinGroup}
import hobby.chenai.nakam.txdsl.core.exch.AbsExchange

import scala.language.implicitConversions

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 29/05/2017
  */
object DSL {
  val ~>: = Ops

  def \(x: Any): Unit = println(x)

  def ln(): Unit = \("")

  def symbols[E](count: Int)(implicit elem: E = " ", list: List[E] = Nil): List[E] = {
    if (count <= 0) list
    else elem :: symbols(count - 1)(elem, list)
  }

  def fill2Length(any: Any, length: Int, prefix$suf: Boolean = true): String = {
    val syms = symbols(length - any.toString.length)().mkString("")
    if (prefix$suf) syms + any else any + syms
  }

  object Action extends Enumeration {
    type ACTION = Act
    private[Action] class Act(val msg: String, val symbol: String) extends Val {
      override def toString() = prefix + " " + msg

      lazy val prefix = symbols(9 - msg.length)(symbol).mkString("")
      lazy val suffix = symbols(3)(symbol).mkString("")
    }
    val BUY = new Act("buy", "<")
    val SALE = new Act("sale", ">")
    val CANCEL = new Act("cancel", "!")
  }

  import Action._

  object Ops {
    def buy(token: AbsCoinGroup#AbsCoin) = new Ops(BUY, token)

    def sale(token: AbsCoinGroup#AbsCoin) = new Ops(SALE, token)

    def cancel(token: AbsCoinGroup#AbsCoin) = new Ops(CANCEL, token)
  }

  class Ops(val action: ACTION, val token: AbsCoinGroup#AbsCoin) {
    private implicit var exchange: AbsExchange = _
    private var cash: AbsCashGroup#AbsCoin = _

    def on(exchange: AbsExchange): Ops = {
      this.exchange = exchange
      this
    }

    def use(cash: AbsCashGroup#AbsCoin): Ops = {
      this.cash = cash
      this
    }

    def ~>= : Ops = {
      println(s"$action: ${token.formatted(15)()} on ${exchange.name}" +
        s" use ${(if (cash == null) token to exchange.pricingCash.unitStd else cash).formatted(15)()}" +
        s" ${action.suffix} scheduling...")
      this
    }
  }
}

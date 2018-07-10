package hobby.chenai.nakam.autotx

import hobby.chenai.nakam.autotx.core.coin.AbsCoinGroup
import hobby.chenai.nakam.lang.TypeBring

/**
  * @author Chenai Nakam(chenai.nakam@gmail.com)
  * @version 1.0, 10/07/2018
  */
object coinTpeImpl {
  def apply[PriTC <: AbsCoinGroup#AbsCoin, PriCC <: AbsCoinGroup#AbsCoin]: coinTpeImpl[PriTC, PriCC] = new coinTpeImpl[PriTC, PriCC] {}
}

trait coinTpeImpl[PriTC <: AbsCoinGroup#AbsCoin, PriCC <: AbsCoinGroup#AbsCoin] {
  implicit lazy val ptc = new TypeBring[PriTC, PriTC, AbsCoinGroup#AbsCoin] {}.t2
  implicit lazy val pcc = new TypeBring[PriCC, PriCC, AbsCoinGroup#AbsCoin] {}.t2
}

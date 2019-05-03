package core.entity2.traits

import core.entity2.Entity2

trait TurnCounterHolder2 extends Entity2 {
    val turn: Long
    
    def nextTurn: TurnCounterHolder2
}
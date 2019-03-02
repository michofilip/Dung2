package core.entities.properties

import core.entities.Entity

trait TurnCounterHolder[T <: TurnCounterHolder[T]] extends Entity {
    val turn: Long
    
    def nextTurn: T
}
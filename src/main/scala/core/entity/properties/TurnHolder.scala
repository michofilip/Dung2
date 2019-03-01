package core.entity.properties

import core.entity.Entity

trait TurnHolder[T <: TurnHolder[T]] extends Entity[T] {
    val turn: Long
    
    def nextTurn: T
}
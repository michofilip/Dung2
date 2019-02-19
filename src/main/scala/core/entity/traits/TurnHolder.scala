package core.entity.traits

import core.entity.Entity

trait TurnHolder extends Entity {
    val turn: Long
    
    def nextTurn: T
}
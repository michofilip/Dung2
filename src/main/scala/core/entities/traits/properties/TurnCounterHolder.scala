package core.entities.traits.properties

import core.entities.Entity

trait TurnCounterHolder extends Entity {
    val turn: Long
    
    def nextTurn: TurnCounterHolder
}

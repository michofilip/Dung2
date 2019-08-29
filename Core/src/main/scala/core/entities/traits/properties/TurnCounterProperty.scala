package core.entities.traits.properties

import core.entities.Entity

trait TurnCounterProperty extends Entity {
    val turn: Long
    
    def nextTurn: TurnCounterProperty
}

package core.entities.finals.nonmap

import core.entities.traits.properties.TurnCounterProperty

final class TurnCounter(override val id: Long,
                        override val turn: Long
                        ) extends TurnCounterProperty {
    private def update(turn: Long = turn): TurnCounter = {
        new TurnCounter(id, turn)
    }
    
    override def nextTurn: TurnCounter = {
        update(turn = turn)
    }
}
package core.entities.finals.nonmap

import core.entities.traits.properties.TurnCounterHolder

final class TurnCounter(override val id: Long,
                        override val turn: Long
                        ) extends TurnCounterHolder {
    private def update(turn: Long = turn): TurnCounter = {
        new TurnCounter(id, turn)
    }
    
    override def nextTurn: TurnCounter = {
        update(turn = turn)
    }
}
package core.entity2.finals

import core.entity2.traits.TurnCounterHolder2

final class TurnCounter2(override val id: Long,
                         override val turn: Long
                        ) extends TurnCounterHolder2 {
    private def update(turn: Long = turn): TurnCounter2 = {
        new TurnCounter2(id, turn)
    }
    
    override def nextTurn: TurnCounter2 = {
        update(turn = turn)
    }
}
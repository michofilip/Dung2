package core.entity2.finals

import core.entities.selectors.{AnimationSelector, PhysicsSelector}
import core.entity2.traits._
import core.parts.position.Position
import core.parts.state.State

final class LockableDoor2(override val id: Long,
                          override val position: Position,
                          override protected val physicsSelector: PhysicsSelector,
                          override protected val animationSelector: AnimationSelector,
                          override val state: State,
                          override val lockCode: Long,
                          override val openingDuration: Int,
                          override val closingDuration: Int,
                          override val unlockingDuration: Int,
                          override val lockingDuration: Int
                         ) extends PositionHolder2 with PhysicsHolder2 with AnimationHolder2 with ClosingCapable with LockingCapable {
    
    private def update(position: Position = position, state: State = state): LockableDoor2 = {
        new LockableDoor2(id, position, physicsSelector, animationSelector, state, lockCode, openingDuration, closingDuration, unlockingDuration, lockingDuration)
    }
    
    override protected def setPosition(position: Position): LockableDoor2 = {
        update(position = position)
    }
    
    override protected def setState(state: State): LockableDoor2 = {
        update(state = state)
    }
    
}
package core.entities.finals.map

import core.entities.traits.templates.{ClosingCapable, LockingCapable}
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.state.State.{Close, Unlocked}
import core.parts.timer.TimeStamp

final class LockableDoor(override val id: Long,
                         override val position: Position,
                         override val state: State,
                         override val lockCode: Long,
                         override protected val animationBeginningTimeStamp: TimeStamp,
                         override protected val physicsSelector: PhysicsSelector,
                         override protected val animationSelector: AnimationSelector
                         ) extends ClosingCapable with LockingCapable {
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp
                      ): LockableDoor = {
        new LockableDoor(id, position, state, lockCode, animationBeginningTimeStamp, physicsSelector, animationSelector)
    }
    
    override protected def setPosition(position: Position): LockableDoor = {
        update(position = position)
    }
    
    override protected def setState(state: State): LockableDoor = {
        state match {
            case Unlocked => update(state = Close)
            case _ => update(state = state)
        }
    }
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): LockableDoor = {
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
    }
}
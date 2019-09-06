package core.entities.finals.map

import core.entities.traits.templates.ClosingCapable
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.timer.TimeStamp

final class SimpleDoor(override val id: Long,
                       override val position: Position,
                       override val state: State,
                       override protected val animationBeginningTimeStamp: TimeStamp,
                       override protected val physicsSelector: PhysicsSelector,
                       override protected val animationSelector: AnimationSelector
                       ) extends ClosingCapable {
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp
                      ): SimpleDoor = {
        new SimpleDoor(id, position, state, animationBeginningTimeStamp, physicsSelector, animationSelector)
    }
    
    override protected def setPosition(position: Position): SimpleDoor = {
        update(position = position)
    }
    
    override protected def setState(state: State): SimpleDoor = {
        update(state = state)
    }
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): SimpleDoor = {
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
    }
    
}
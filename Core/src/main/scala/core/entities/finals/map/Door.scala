package core.entities.finals.map

import core.entities.traits.templates.ClosableTemplate
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.timer.TimeStamp

final class Door(override val id: Long,
                 override val position: Position,
                 override val state: State,
                 override val lockCode: Long,
                 override protected val animationBeginningTimeStamp: TimeStamp,
                 override protected val physicsSelector: PhysicsSelector,
                 override protected val animationSelector: AnimationSelector
                ) extends ClosableTemplate {
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Door =
        new Door(id, position, state, lockCode, animationBeginningTimeStamp, physicsSelector, animationSelector)
    
    override protected def setPosition(position: Position): Door =
        update(position = position)
    
    override protected def setState(state: State): Door =
        update(state = state)
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Door =
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
}
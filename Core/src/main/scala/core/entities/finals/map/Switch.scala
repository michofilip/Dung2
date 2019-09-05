package core.entities.finals.map

import core.entities.traits.templates.SwitchTemplate
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.timer.TimeStamp

final class Switch(override val id: Long,
                   override val position: Position,
                   override val state: State,
                   override protected val animationBeginningTimeStamp: TimeStamp,
                   override protected val physicsSelector: PhysicsSelector,
                   override protected val animationSelector: AnimationSelector
                  ) extends SwitchTemplate {
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Switch =
        new Switch(id, position, state, animationBeginningTimeStamp, physicsSelector, animationSelector)
    
    override protected def setPosition(position: Position): Switch =
        update(position = position)
    
    override protected def setState(state: State): Switch =
        update(state = state)
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Switch =
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
}
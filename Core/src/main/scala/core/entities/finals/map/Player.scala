package core.entities.finals.map

import core.entities.traits.templates.CharacterTemplate
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.timer.TimeStamp

final class Player(override val id: Long,
                   override val position: Position,
                   override val state: State,
                   override protected val animationBeginningTimeStamp: TimeStamp,
                   override protected val physicsSelector: PhysicsSelector,
                   override protected val animationSelector: AnimationSelector
                  ) extends CharacterTemplate {
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Player =
        new Player(id, position, state, animationBeginningTimeStamp, physicsSelector, animationSelector)
    
    override protected def setPosition(position: Position): Player =
        update(position = position)
    
    override protected def setState(state: State): Player =
        update(state = state)
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Player =
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
}
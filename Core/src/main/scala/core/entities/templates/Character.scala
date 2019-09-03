package core.entities.templates

import core.entities.Entity
import core.entities.traits.properties.{AnimationProperty, InventoryProperty, PhysicsProperty, PositionProperty, StateProperty}
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.timer.TimeStamp

class Character(override val id: Long,
                override val position: Position,
                override val state: State,
                override protected val animationBeginningTimeStamp: TimeStamp,
                override protected val physicsSelector: PhysicsSelector,
                override protected val animationSelector: AnimationSelector)
        extends Entity with PositionProperty with PhysicsProperty with AnimationProperty with StateProperty
                with InventoryProperty {
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Character =
        new Character(id, position, state, animationBeginningTimeStamp, physicsSelector, animationSelector)
    
    override protected def setPosition(position: Position): Character =
        update(position = position)
    
    override protected def setState(state: State): Character =
        update(state = state)
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Character =
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
}

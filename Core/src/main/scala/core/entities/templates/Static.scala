package core.entities.templates

import core.entities.Entity
import core.entities.traits.properties.{AnimationProperty, PhysicsProperty, PositionProperty}
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.timer.TimeStamp

class Static(override val id: Long,
             override val position: Position,
             override protected val animationBeginningTimeStamp: TimeStamp,
             override protected val physicsSelector: PhysicsSelector,
             override protected val animationSelector: AnimationSelector)
        extends Entity with PositionProperty with PhysicsProperty with AnimationProperty {
    
    private def update(position: Position = position,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Static =
        new Static(id, position, animationBeginningTimeStamp, physicsSelector, animationSelector)
    
    override protected def setPosition(position: Position): Static =
        update(position = position)
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Static =
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
}

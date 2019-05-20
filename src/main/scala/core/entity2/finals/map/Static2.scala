package core.entity2.finals.map

import core.entity2.traits.templates.SimpleEntity
import core.parts.graphics.AnimationSelector2
import core.parts.physics.PhysicsSelector2
import core.parts.position.Position
import core.parts.timer.TimeStamp

final class Static2(override val id: Long,
                    override val position: Position,
                    override protected val animationBeginningTimeStamp: TimeStamp,
                    override protected val physicsSelector: PhysicsSelector2,
                    override protected val animationSelector: AnimationSelector2
                   ) extends SimpleEntity {
    
    private def update(position: Position = position,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Static2 = {
        new Static2(id, position, animationBeginningTimeStamp, physicsSelector, animationSelector)
    }
    
    override protected def setPosition(position: Position): Static2 = {
        update(position = position)
    }
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Static2 = {
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
    }
}
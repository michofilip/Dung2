package core.entity2.finals

import core.entities.selectors.{AnimationSelector, PhysicsSelector}
import core.entity2.traits.{AnimationHolder2, PhysicsHolder2, PositionHolder2}
import core.parts.position.Position

class Static2(override val id: Long,
              override val position: Position,
              override protected val physicsSelector: PhysicsSelector,
              override protected val animationSelector: AnimationSelector
             ) extends PositionHolder2 with PhysicsHolder2 with AnimationHolder2 {
    
    private def update(position: Position = position): Static2 = {
        new Static2(id, position, physicsSelector, animationSelector)
    }
    
    override protected def setPosition(position: Position): Static2 = {
        update(position = position)
    }
    
}
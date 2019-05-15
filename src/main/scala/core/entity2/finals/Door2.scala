package core.entity2.finals

import core.entities.selectors.{AnimationSelector, PhysicsSelector}
import core.entity2.traits._
import core.parts.position.Position
import core.parts.state.State

final class Door2(override val id: Long,
                  override val position: Position,
                  override protected val physicsSelector: PhysicsSelector,
                  override protected val animationSelector: AnimationSelector,
                  override val state: State,
                  override val openingDuration: Int,
                  override val closingDuration: Int
                 ) extends PositionHolder2 with PhysicsHolder2 with AnimationHolder2 with ClosingCapable {
    
    private def update(position: Position = position, state: State = state): Door2 = {
        new Door2(id, position, physicsSelector, animationSelector, state, openingDuration, closingDuration)
    }
    
    override protected def setPosition(position: Position): Door2 = {
        update(position = position)
    }
    
    override protected def setState(state: State): Door2 = {
        update(state = state)
    }
    
    
}
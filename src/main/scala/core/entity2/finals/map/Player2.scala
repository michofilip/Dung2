package core.entity2.finals.map

import core.entity2.traits.properties.{AnimationHolder2, PhysicsHolder2, PositionHolder2}
import core.entity2.traits.templates.CharacterLike
import core.parts.position.Position
import core.parts.state.State

final class Player2(override val id: Long,
                    override val position: Position,
                    override protected val physicsSelector: PhysicsSelector,
                    override protected val animationSelector: AnimationSelector,
                    override val state: State
                   ) extends PositionHolder2 with PhysicsHolder2 with AnimationHolder2 with CharacterLike {
    
    private def update(position: Position = position, state: State = state): Player2 = {
        new Player2(id, position, physicsSelector, animationSelector, state)
    }
    
    override protected def setPosition(position: Position): Player2 = {
        update(position = position)
    }
    
    override protected def setState(state: State): Player2 = {
        update(state = state)
    }
}
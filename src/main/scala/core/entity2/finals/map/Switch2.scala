package core.entity2.finals.map

import core.entity2.traits.properties.{AnimationHolder2, PhysicsHolder2, PositionHolder2}
import core.entity2.traits.templates.SwitchingCapable
import core.parts.position.Position
import core.parts.state.State

final class Switch2(override val id: Long,
                    override val position: Position,
                    override protected val physicsSelector: PhysicsSelector,
                    override protected val animationSelector: AnimationSelector,
                    override val state: State,
                    override val switchingOffDuration: Int,
                    override val switchingOnDuration: Int
                   ) extends PositionHolder2 with PhysicsHolder2 with AnimationHolder2 with SwitchingCapable {
    
    private def update(position: Position = position, state: State = state): Switch2 = {
        new Switch2(id, position, physicsSelector, animationSelector, state, switchingOffDuration, switchingOnDuration)
    }
    
    override protected def setPosition(position: Position): Switch2 = {
        update(position = position)
    }
    
    override protected def setState(state: State): Switch2 = {
        update(state = state)
    }
}
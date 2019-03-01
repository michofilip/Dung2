package core.entity

import core.parts.position.Position
import core.parts.state.State
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import json.{JValue, MyJ}

final class Switch(override val id: String,
                   override val position: Position,
                   override protected val physicsSelector: PhysicsSelector,
                   override protected val animationSelector: AnimationSelector,
                   override protected val animationStartTime: Long,
                   override val state: State
                  ) extends Switchable {
    
//    override protected type T >: Switch
    
    val switchingOffLength = 1000
    val switchingOnLength = 1000
    
    private def update(position: Position = position, animationStartTime: Long = animationStartTime, state: State = state): Switch = {
        new Switch(id, position, physicsSelector, animationSelector, animationStartTime, state)
    }
    
    override protected def setPosition(position: Position): Switch = {
        update(position = position)
    }
    
    override protected def setState(state: State): Switch = {
        update(state = state)
    }
    
    override protected def setAnimationStartTime(animationStartTime: Long): Switch = {
        update(animationStartTime = animationStartTime)
    }
    
    override def toJSON: JValue = {
        MyJ.jObject(
            "entity" -> "Switch",
            "id" -> id,
            "position" -> position,
            "physicsSelector" -> physicsSelector.id,
            "animationSelector" -> animationSelector.id,
            "animationStartTime" -> animationStartTime,
            "state" -> state.toString
        )
    }
}

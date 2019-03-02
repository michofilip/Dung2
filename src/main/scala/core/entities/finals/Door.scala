package core.entities.finals

import core.entities.selectors.{AnimationSelector, PhysicsSelector}
import core.entities.templates.Openable
import core.parts.position.Position
import core.parts.state.State
import json.{JValue, MyJ}

final class Door(override val id: String,
                 override val position: Position,
                 override protected val physicsSelector: PhysicsSelector,
                 override protected val animationSelector: AnimationSelector,
                 override protected val animationStartTime: Long,
                 override val state: State,
                 val lockCode: Long
                ) extends Openable[Door] {
    
    private def update(position: Position = position, animationStartTime: Long = animationStartTime, state: State = state): Door = {
        new Door(id, position, physicsSelector, animationSelector, animationStartTime, state, lockCode)
    }
    
    override protected def setPosition(position: Position): Door = {
        update(position = position)
    }
    
    override protected def setAnimationStartTime(animationStartTime: Long): Door = {
        update(animationStartTime = animationStartTime)
    }
    
    override protected def setState(state: State): Door = {
        update(state = state)
    }
    
    override def toJSON: JValue = {
        MyJ.jObject(
            "class" -> "Door",
            "id" -> id,
            "position" -> position,
            "physicsSelector" -> physicsSelector.id,
            "animationSelector" -> animationSelector.id,
            "animationStartTime" -> animationStartTime,
            "state" -> state.toString,
            "lockCode" -> lockCode
        )
    }
}

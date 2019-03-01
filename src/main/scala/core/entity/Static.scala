package core.entity

import core.parts.position.Position
import core.entity.properties.{AnimationHolder, PhysicsHolder, PositionHolder}
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import json.{JValue, MyJ}

final class Static(override val id: String,
                   override val position: Position,
                   override protected val physicsSelector: PhysicsSelector,
                   override protected val animationSelector: AnimationSelector,
                   override protected val animationStartTime: Long
                  ) extends Entity with PositionHolder with PhysicsHolder with AnimationHolder {
    
    override protected type T = Static
    //    override val id: String = _id
    //    override val position: Position = _position
    //    override protected val physicsSelector: PhysicsSelector = _physicsSelector
    //    override protected val animationSelector: AnimationSelector = _animationSelector
    //    override protected val animationStartTime: Long = _animationStartTime
    
    private def update(position: Position = position): T = {
        new Static(id, position, physicsSelector, animationSelector, animationStartTime)
    }
    
    override def setPosition(position: Position): Static = {
        update(position = position)
    }
    
    override def toJSON: JValue = {
        MyJ.jObject(
            "entity" -> "Static",
            "id" -> id,
            "position" -> position,
            "physicsSelector" -> physicsSelector.id,
            "animationSelector" -> animationSelector.id,
            "animationStartTime" -> animationStartTime
        )
    }
    
    override protected def setAnimationStartTime(animationStartTime: Long): Static = ???
}
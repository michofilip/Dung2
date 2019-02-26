package core.entity

import core.entity.properties.position.Position
import core.entity.properties.{AnimationHolder, PhysicsHolder, PositionHolder}
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import json.{JValue, MyJ}

final class Static(_id: String,
                   _position: Position,
                   _physicsSelector: PhysicsSelector,
                   _animationSelector: AnimationSelector,
                   _animationStartTime: Long)
        extends Entity with PositionHolder with PhysicsHolder with AnimationHolder {
    
    override protected type T = Static
    override val id: String = _id
    override val position: Position = _position
    override protected val physicsSelector: PhysicsSelector = _physicsSelector
    override protected val animationSelector: AnimationSelector = _animationSelector
    override protected val animationStartTime: Long = _animationStartTime
    
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
}
package core.entity

import core.entity.properties.position.Position
import core.entity.properties.state.State
import core.entity.properties.state.State.{Off, On, SwitchingOff, SwitchingOn}
import core.entity.properties.{AnimationHolder, PhysicsHolder, PositionHolder, StateHolder}
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import json.{JValue, MyJ}

final class Switch(_id: String,
                   _position: Position,
                   _physicsSelector: PhysicsSelector,
                   _animationSelector: AnimationSelector,
                   _animationStartTime: Long,
                   _state: State)
        extends Entity with PositionHolder with PhysicsHolder with AnimationHolder with StateHolder {
    
    override protected type T = Switch
    override val id: String = _id
    override val position: Position = _position
    override protected val physicsSelector: PhysicsSelector = _physicsSelector
    override protected val animationSelector: AnimationSelector = _animationSelector
    override protected val animationStartTime: Long = _animationStartTime
    override val state: State = _state
    
    val switchingOffLength = 1000
    val switchingOnLength = 1000
    
    private def update(position: Position = position, animationStartTime: Long = animationStartTime, state: State = state): T = {
        new Switch(id, position, physicsSelector, animationSelector, animationStartTime, state)
    }
    
    override protected def setPosition(position: Position): Switch = {
        update(position = position)
    }
    
    def beginSwitchingOff(time: Long): T = {
        update(animationStartTime = time, state = SwitchingOff)
    }
    
    def finishSwitchingOff(time: Long): T = {
        update(animationStartTime = time, state = Off)
    }
    
    def beginSwitchingOn(time: Long): T = {
        update(animationStartTime = time, state = SwitchingOn)
    }
    
    def finishSwitchingOn(time: Long): T = {
        update(animationStartTime = time, state = On)
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

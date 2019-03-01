package core.entity

import core.parts.position.Position
import core.parts.state.State
import core.parts.state.State._
import core.entity.properties.{AnimationHolder, PhysicsHolder, PositionHolder, StateHolder}
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import json.{JValue, MyJ}

final class Door(override val id: String,
                 override val position: Position,
                 override protected val physicsSelector: PhysicsSelector,
                 override protected val animationSelector: AnimationSelector,
                 override protected val animationStartTime: Long,
                 override val state: State,
                 val lockCode: Long
                ) extends Entity with PositionHolder with PhysicsHolder with AnimationHolder with StateHolder {

    override protected type T = Door
    //    override val id: String = _id
    //    override val position: Position = _position
    //    override protected val physicsSelector: PhysicsSelector = _physicsSelector
    //    override protected val animationSelector: AnimationSelector = _animationSelector
    //    override protected val animationStartTime: Long = _animationStartTime
    //    override val state: State = _state
    //    val lockCode: Long = _lockCode

    val openingLength = 1000
    val closingLength = 1000
    val unlockingLength = 1000
    val lockingLength = 1000

    private def update(position: Position = position, animationStartTime: Long = animationStartTime, state: State = state): T = {
        new Door(id, position, physicsSelector, animationSelector, animationStartTime, state, lockCode)
    }

    override protected def setPosition(position: Position): Door = {
        update(position = position)
    }

    def beginOpening(time: Long): T = {
        update(animationStartTime = time, state = Opening)
    }

    def finishOpening(time: Long): T = {
        update(animationStartTime = time, state = Open)
    }

    def beginClosing(time: Long): T = {
        update(animationStartTime = time, state = Closing)
    }

    def finishClosing(time: Long): T = {
        update(animationStartTime = time, state = Close)
    }

    def beginUnlocking(time: Long): T = {
        update(animationStartTime = time, state = Unlocking)
    }

    def finishUnlocking(time: Long): T = {
        update(animationStartTime = time, state = Close)
    }

    def beginLocking(time: Long): T = {
        update(animationStartTime = time, state = Locking)
    }

    def finishLocking(time: Long): T = {
        update(animationStartTime = time, state = Locked)
    }

    override def toJSON: JValue = {
        MyJ.jObject(
            "entity" -> "Switch",
            "id" -> id,
            "position" -> position,
            "physicsSelector" -> physicsSelector.id,
            "animationSelector" -> animationSelector.id,
            "animationStartTime" -> animationStartTime,
            "state" -> state.toString,
            "lockCode" -> lockCode
        )
    }
    
    override protected def setState(state: State): Door = ???
    
    override protected def setAnimationStartTime(animationStartTime: Long): Door = ???
}

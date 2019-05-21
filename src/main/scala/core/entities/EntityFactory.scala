package core.entities

import core.entities.finals.map.{LockableDoor, Switch}
import core.parts.graphics.AnimationSelector.{DoorAnimationSelector, LeverAnimationSelector}
import core.parts.physics.PhysicsSelector.{DoorPhysicsSelector, LeverPhysicsSelector}
import core.parts.position.Direction.North
import core.parts.position.{Coordinates, Position}
import core.parts.state.State._
import core.parts.timer.Timer

class EntityFactory(private val timer: Timer) {
    // todo redo it
    def create(name: String, args: String*): Option[Entity] = {
        name match {
            case "lever" => Some(lever())
            case "door" => Some(door())
            case _ => None
        }
    }
    
    private def lever(): Switch = {
        val id = 1000
        val position = Position(coordinates = Coordinates(10, 20), direction = North, canMove = true, canRotate = true)
        val physicsSelector = LeverPhysicsSelector
        val animationSelector = LeverAnimationSelector
        val animationBeginningTimeStamp = timer.getTimeStamp
        val state = Off
        
        new Switch(id, position, state, animationBeginningTimeStamp, physicsSelector, animationSelector)
    }
    
    private def door(): LockableDoor = {
        val id = 1001
        val position = Position(coordinates = Coordinates(10, 20), direction = North, canMove = true, canRotate = true)
        val physicsSelector = DoorPhysicsSelector
        val animationSelector = DoorAnimationSelector
        val animationBeginningTimeStamp = timer.getTimeStamp
        val state = Open
        val lockCode = 1000
        
        new LockableDoor(id, position, state, lockCode, animationBeginningTimeStamp, physicsSelector, animationSelector)
    }
}

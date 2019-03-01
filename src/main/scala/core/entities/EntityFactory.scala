package core.entities

import core.entities.finals.{Door, Switch}
import core.entities.selectors.AnimationSelector._
import core.entities.selectors.PhysicsSelector._
import core.parts.position.Direction.North
import core.parts.position.{Coordinates, Position}
import core.parts.state.State._
import core.parts.timer.Timer

class EntityFactory(private val clock: Timer) {
    // todo redo it
    def create(name: String, args: String*): Option[Entity[_]] = {
        name match {
            case "lever" => Some(lever())
            case "door" => Some(door())
            case _ => None
        }
    }
    
    private def lever(): Switch = {
        val id = "1000"
        val position = Position(Coordinates(10, 20), North)
        val physicsSelector = LeverPhysicsSelector
        val animationSelector = LeverAnimationSelector
        val animationStartTime = clock.getTime
        val state = Off
        
        new Switch(id, position, physicsSelector, animationSelector, animationStartTime, state)
    }
    
    private def door(): Door = {
        val id = "1001"
        val position = Position(Coordinates(10, 20), North)
        val physicsSelector = DoorPhysicsSelector
        val animationSelector = DoorAnimationSelector
        val animationStartTime = clock.getTime
        val state = Open
        val lockCode = 1000
        
        new Door(id, position, physicsSelector, animationSelector, animationStartTime, state, lockCode)
    }
}

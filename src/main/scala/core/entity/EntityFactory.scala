package core.entity

import core.entity.properties.position.Direction.North
import core.entity.properties.position.{Coordinates, Position}
import core.entity.properties.state.State._
import core.entity.selectors.AnimationSelector._
import core.entity.selectors.PhysicsSelector._
import core.timer.Timer

class EntityFactory(private val clock: Timer) {
    // todo redo it
    def create(name: String, args: String*): Option[Entity] = {
        name match {
            case "lever" => Some(lever())
            case "door" => Some(door())
            case _ => None
        }
    }
    
    private def lever(): Entity = {
        val id = "1000"
        val state = Off
        val position = Position(Coordinates(10, 20), North)
        val physicsSelector = LeverPhysicsSelector
        val animationSelector = LeverAnimationSelector
        
        new Entity.Switch(id, clock.getTime, state, position, physicsSelector, animationSelector)
    }
    
    private def door(): Entity = {
        val id = "1001"
        val state = Open
        val position = Position(Coordinates(10, 10), North)
        val lockCode = 1000
        val physicsSelector = LeverPhysicsSelector
        val animationSelector = LeverAnimationSelector
        
        new Entity.Door(id, clock.getTime, state, position, lockCode, physicsSelector, animationSelector)
    }
}

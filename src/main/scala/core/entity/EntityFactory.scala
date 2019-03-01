package core.entity

import core.parts.position.Direction.North
import core.parts.position.{Coordinates, Position}
import core.parts.state.State._
import core.entity.selectors.AnimationSelector._
import core.entity.selectors.PhysicsSelector._
import core.parts.timer.Timer

class EntityFactory(private val clock: Timer) {
    // todo redo it
    def create(name: String, args: String*): Option[Entity] = {
        name match {
            case "lever" => Some(lever())
            //            case "door" => Some(door())
            case _ => None
        }
    }
    
    private def lever(): Entity = {
        val id = "1000"
        val position = Position(Coordinates(10, 20), North)
        val physicsSelector = LeverPhysicsSelector
        val animationSelector = LeverAnimationSelector
        val animationStartTime = clock.getTime
        val state = Off
        
        new Switch(id, position, physicsSelector, animationSelector, animationStartTime, state)
    }
    
    //    private def door(): Entity = {
    //        val id = "1001"
    //        val state = Open
    //        val position = Position(Coordinates(10, 10), North)
    //        val lockCode = 1000
    //        val physicsSelector = LeverPhysicsSelector
    //        val animationSelector = LeverAnimationSelector
    //
    //        new Entity.Door(id, clock.getTime, state, position, lockCode, physicsSelector, animationSelector)
    //    }
}

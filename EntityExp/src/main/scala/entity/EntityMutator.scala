package entity

import entity.parts.Category._
import entity.parts.State._
import entity.parts.{Animation, Coordinates, Direction, Physics, State}
import entity.selectors.{AnimationSelector, PhysicsSelector}

object EntityMutator {
    
    implicit class StateMutator(entity: Entity) {
        def setState(entityState: State): Entity = entity.copy(state = Some(entityState), timestamp = 0)
        
        def removeState(): Entity = entity.copy(state = None)
        
        def updateState(entityState: State): Entity = entity.state match {
            case Some(_) => setState(entityState)
            case _ => entity
        }
    }
    
    implicit class CoordinatesMutator(entity: Entity) {
        def setCoordinates(coordinates: Coordinates): Entity = entity.copy(coordinates = Some(coordinates))
        
        def removeCoordinates(): Entity = entity.copy(coordinates = None)
        
        def moveTo(x: Int, y: Int): Entity = entity.coordinates match {
            case Some(_) => setCoordinates(Coordinates(x, y))
            case _ => entity
        }
        
        def moveBy(dx: Int, dy: Int): Entity = entity.coordinates match {
            case Some(Coordinates(x, y)) => setCoordinates(Coordinates(x + dx, y + dy))
            case _ => entity
        }
    }
    
    implicit class DirectionMutator(entity: Entity) {
        def setDirection(direction: Direction): Entity = entity.copy(direction = Some(direction))
        
        def removeDirection(): Entity = entity.copy(direction = None)
        
        def turnTo(direction: Direction): Entity = entity.direction match {
            case Some(_) => setDirection(direction)
            case _ => entity
        }
        
        def turnRight(): Entity = entity.direction match {
            case Some(direction) => setDirection(direction.turnRight)
            case _ => entity
        }
        
        def turnLeft(): Entity = entity.direction match {
            case Some(direction) => setDirection(direction.turnLeft)
            case _ => entity
        }
        
        def turnBack(): Entity = entity.direction match {
            case Some(direction) => setDirection(direction.turnBack)
            case _ => entity
        }
    }
    
    implicit class PhysicsMutator(entity: Entity)(implicit physicsSelector: PhysicsSelector) {
        def setPhysics(physics: Physics): Entity = entity.copy(physics = Some(physics))
        
        def removePhysics(): Entity = entity.copy(physics = None)
        
        def selectPhysics(): Entity = physicsSelector.select(entity.category, entity.state) match {
            case Some(physics) => entity.setPhysics(physics)
            case None => entity.removePhysics()
        }
    }
    
    implicit class AnimationMutator(entity: Entity)(implicit animationSelector: AnimationSelector) {
        def setAnimation(animation: Animation): Entity = entity.copy(animation = Some(animation))
        
        def removeAnimation(): Entity = entity.copy(state = None)
        
        def selectAnimation(): Entity = animationSelector.select(entity.category, entity.state, entity.direction) match {
            case Some(animation) => entity.setAnimation(animation)
            case None => entity.removeAnimation()
        }
    }
    
    implicit class SwitchMutator(entity: Entity)(implicit
                                                 physicsSelector: PhysicsSelector,
                                                 animationSelector: AnimationSelector) {
        def beginSwitchingOff(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(On)) => entity
                    .updateState(SwitchingOff)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
        
        def finishSwitchingOff(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(SwitchingOff)) => entity
                    .updateState(Off)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
        
        def beginSwitchingOn(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(Off)) => entity
                    .updateState(SwitchingOn)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
        
        def finishSwitchingOn(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(SwitchingOn)) => entity
                    .updateState(On)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
    }
    
}

package entity

import parts.Category._
import parts.State._
import parts.{Animation, Position, Direction, Physics, State}
import selectors.{AnimationSelector, PhysicsSelector}

object EntityMutator {
    
    implicit class StateMutator(entity: Entity) {
        def setState(entityState: State): Entity = entity.copy(stateOpt = Some(entityState), timestamp = 0)
        
        def removeState(): Entity = entity.copy(stateOpt = None)
        
        def updateState(entityState: State): Entity = entity.stateOpt match {
            case Some(_) => setState(entityState)
            case _ => entity
        }
    }
    
    implicit class PositionMutator(entity: Entity) {
        def setPosition(position: Position): Entity = entity.copy(positionOpt = Some(position))
        
        def removePosition(): Entity = entity.copy(positionOpt = None)
        
        def moveTo(x: Int, y: Int): Entity = entity.positionOpt match {
            case Some(_) => setPosition(Position(x, y))
            case _ => entity
        }
        
        def moveBy(dx: Int, dy: Int): Entity = entity.positionOpt match {
            case Some(Position(x, y)) => setPosition(Position(x + dx, y + dy))
            case _ => entity
        }
    }
    
    implicit class DirectionMutator(entity: Entity) {
        def setDirection(direction: Direction): Entity = entity.copy(directionOpt = Some(direction))
        
        def removeDirection(): Entity = entity.copy(directionOpt = None)
        
        def turnTo(direction: Direction): Entity = entity.directionOpt match {
            case Some(_) => setDirection(direction)
            case _ => entity
        }
        
        def turnRight(): Entity = entity.directionOpt match {
            case Some(direction) => setDirection(direction.turnRight)
            case _ => entity
        }
        
        def turnLeft(): Entity = entity.directionOpt match {
            case Some(direction) => setDirection(direction.turnLeft)
            case _ => entity
        }
        
        def turnBack(): Entity = entity.directionOpt match {
            case Some(direction) => setDirection(direction.turnBack)
            case _ => entity
        }
    }
    
    implicit class PhysicsMutator(entity: Entity)(implicit physicsSelector: PhysicsSelector) {
        def setPhysics(physics: Physics): Entity = entity.copy(physicsOpt = Some(physics))
        
        def removePhysics(): Entity = entity.copy(physicsOpt = None)
        
        def selectPhysics(): Entity = physicsSelector.select(entity.category, entity.stateOpt) match {
            case Some(physics) => entity.setPhysics(physics)
            case None => entity.removePhysics()
        }
    }
    
    implicit class AnimationMutator(entity: Entity)(implicit animationSelector: AnimationSelector) {
        def setAnimation(animation: Animation): Entity = entity.copy(animationOpt = Some(animation))
        
        def removeAnimation(): Entity = entity.copy(stateOpt = None)
        
        def selectAnimation(): Entity = animationSelector.select(entity.category, entity.stateOpt, entity.directionOpt) match {
            case Some(animation) => entity.setAnimation(animation)
            case None => entity.removeAnimation()
        }
    }
    
    implicit class SwitchMutator(entity: Entity)(implicit
                                                 physicsSelector: PhysicsSelector,
                                                 animationSelector: AnimationSelector) {
        def beginSwitchingOff(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(On)) => entity
                    .updateState(SwitchingOff)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
        
        def finishSwitchingOff(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(SwitchingOff)) => entity
                    .updateState(Off)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
        
        def beginSwitchingOn(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(Off)) => entity
                    .updateState(SwitchingOn)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
        
        def finishSwitchingOn(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(SwitchingOn)) => entity
                    .updateState(On)
                    .selectPhysics()
                    .selectAnimation()
            case _ => entity
        }
    }
    
}

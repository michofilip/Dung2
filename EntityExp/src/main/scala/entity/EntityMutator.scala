package entity

import parts.Category._
import parts.State._
import parts.{Direction, Graphics, Physics, Position, State}
import selectors.{AnimationSelector, PhysicsSelector}

object EntityMutator {
    
    implicit class StateMutator(entity: Entity) {
        def setState(entityState: State): Entity = entity.copy(stateOpt = Some(entityState))
        
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
    
    implicit class GraphicsMutator(entity: Entity)(implicit animationSelector: AnimationSelector) {
        def setGraphics(graphics: Graphics): Entity = entity.copy(graphicsOpt = Some(graphics))
        
        def removeGraphics(): Entity = entity.copy(graphicsOpt = None)
        
        def selectGraphics(): Entity = animationSelector.select(entity.category, entity.stateOpt, entity.directionOpt) match {
            case Some(animation) => entity.setGraphics(Graphics(animation = animation, initialTimestamp = 0))
            case None => entity.removeGraphics()
        }
    }
    
    implicit class SwitchMutator(entity: Entity)(implicit
                                                 physicsSelector: PhysicsSelector,
                                                 animationSelector: AnimationSelector) {
        def beginSwitchingOff(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(On)) => entity
                    .updateState(SwitchingOff)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
        
        def finishSwitchingOff(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(SwitchingOff)) => entity
                    .updateState(Off)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
        
        def beginSwitchingOn(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(Off)) => entity
                    .updateState(SwitchingOn)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
        
        def finishSwitchingOn(): Entity = (entity.category, entity.stateOpt) match {
            case (Switch, Some(SwitchingOn)) => entity
                    .updateState(On)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
    }
    
}

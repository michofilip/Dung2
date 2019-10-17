package entity

import entity.parts.Category._
import entity.parts.State._
import entity.parts.{Coordinates, Direction, Graphics, Physics, Position, State}
import entity.selectors.{AnimationSelector, PhysicsSelector}

object EntityMutator {
    
    implicit class TimestampMutator(entity: Entity) {
        def setTimestamp(timestamp: Long): Entity = entity.copy(timestamp = timestamp)
    }
    
    implicit class StateMutator(entity: Entity) {
        def setState(entityState: State): Entity = entity.copy(state = Some(entityState))
        
        def removeState(): Entity = entity.copy(state = None)
        
        def updateState(entityState: State): Entity = entity.state match {
            case Some(_) => setState(entityState)
            case _ => entity
        }
    }
    
    implicit class PositionMutator(entity: Entity) {
        def setPosition(position: Position): Entity = entity.copy(position = Some(position))
        
        def removePosition(): Entity = entity.copy(position = None)
        
        def moveTo(x: Int, y: Int): Entity = entity.position match {
            case Some(position) if position.canMove => setPosition(position.copy(coordinates = Coordinates(x, y)))
            case _ => entity
        }
        
        def moveBy(dx: Int, dy: Int): Entity = entity.position match {
            case Some(position@Position(Coordinates(x, y), _, canMove, _)) if canMove =>
                setPosition(position.copy(coordinates = Coordinates(x + dx, y + dy)))
            case _ => entity
        }
        
        def rotateTo(direction: Direction): Entity = entity.position match {
            case Some(position) if position.canRotate => setPosition(position.copy(direction = direction))
            case _ => entity
        }
        
        def turnRight(): Entity = entity.position match {
            case Some(position@Position(_, direction, _, canRotate)) if canRotate =>
                setPosition(position.copy(direction = direction.turnRight))
            case _ => entity
        }
        
        def turnLeft(): Entity = entity.position match {
            case Some(position@Position(_, direction, _, canRotate)) if canRotate =>
                setPosition(position.copy(direction = direction.turnLeft))
            case _ => entity
        }
        
        def turnBack(): Entity = entity.position match {
            case Some(position@Position(_, direction, _, canRotate)) if canRotate =>
                setPosition(position.copy(direction = direction.turnBack))
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
    
    implicit class GraphicsMutator(entity: Entity)(implicit animationSelector: AnimationSelector) {
        def setGraphics(graphics: Graphics): Entity = entity.copy(graphics = Some(graphics))
        
        def removeGraphics(): Entity = entity.copy(state = None)
        
        def selectGraphics(): Entity =
            animationSelector.select(entity.category, entity.state, entity.position) match {
                case Some(animation) => entity.setGraphics(Graphics(animation, 0))
                case None => entity.removeGraphics()
            }
    }
    
    implicit class SwitchMutator(entity: Entity)
                                (implicit physicsSelector: PhysicsSelector,
                                 animationSelector: AnimationSelector) {
        def beginSwitchingOff(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(On)) => entity
                    .updateState(SwitchingOff)
                    .setTimestamp(0)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
        
        def finishSwitchingOff(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(SwitchingOff)) => entity
                    .updateState(Off)
                    .setTimestamp(0)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
        
        def beginSwitchingOn(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(Off)) => entity
                    .updateState(SwitchingOn)
                    .setTimestamp(0)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
        
        def finishSwitchingOn(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(SwitchingOn)) => entity
                    .updateState(On)
                    .setTimestamp(0)
                    .selectPhysics()
                    .selectGraphics()
            case _ => entity
        }
    }
    
}

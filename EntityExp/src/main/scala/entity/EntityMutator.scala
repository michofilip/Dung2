package entity

import entity.parts.Category._
import entity.parts.Direction.{East, North, NorthEast, NorthWest, South, SouthEast, SouthWest, West}
import entity.parts.State._
import entity.parts.{Coordinates, Direction, Graphics, Physics, Position, State}
import entity.selectors.{GraphicsSelector, PhysicsSelector}

object EntityMutator {
    
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
        
        def rotateRight(): Entity = entity.position match {
            case Some(position@Position(_, direction, _, canRotate)) if canRotate => direction match {
                case North => setPosition(position.copy(direction = NorthEast))
                case NorthEast => setPosition(position.copy(direction = East))
                case East => setPosition(position.copy(direction = SouthEast))
                case SouthEast => setPosition(position.copy(direction = South))
                case South => setPosition(position.copy(direction = SouthWest))
                case SouthWest => setPosition(position.copy(direction = West))
                case West => setPosition(position.copy(direction = NorthWest))
                case NorthWest => setPosition(position.copy(direction = North))
            }
            case _ => entity
        }
        
        def rotateLeft(): Entity = entity.position match {
            case Some(position@Position(_, direction, _, canRotate)) if canRotate => direction match {
                case North => setPosition(position.copy(direction = NorthWest))
                case NorthEast => setPosition(position.copy(direction = North))
                case East => setPosition(position.copy(direction = NorthEast))
                case SouthEast => setPosition(position.copy(direction = East))
                case South => setPosition(position.copy(direction = SouthEast))
                case SouthWest => setPosition(position.copy(direction = South))
                case West => setPosition(position.copy(direction = SouthWest))
                case NorthWest => setPosition(position.copy(direction = West))
            }
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
    
    implicit class GraphicsMutator(entity: Entity)(implicit graphicsSelector: GraphicsSelector) {
        def setGraphics(graphics: Graphics): Entity = entity.copy(graphics = Some(graphics))
        
        def removeGraphics(): Entity = entity.copy(state = None)
        
        def selectGraphics(): Entity =
            graphicsSelector.select(entity.category, entity.state, entity.position.map(_.direction)) match {
                case Some(graphics) => entity.setGraphics(graphics)
                case None => entity.removeGraphics()
            }
    }
    
    implicit class SwitchMutator(entity: Entity)(implicit physicsSelector: PhysicsSelector) {
        def beginSwitchingOff(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(Off)) => entity.updateState(SwitchingOn).selectPhysics()
            case _ => entity
        }
        
        def finishSwitchingOff(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(SwitchingOn)) => entity.updateState(On).selectPhysics()
            case _ => entity
        }
        
        def beginSwitchingOn(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(On)) => entity.updateState(SwitchingOff).selectPhysics()
            case _ => entity
        }
        
        def finishSwitchingOn(): Entity = (entity.category, entity.state) match {
            case (Switch, Some(SwitchingOff)) => entity.updateState(Off).selectPhysics()
            case _ => entity
        }
    }
    
}

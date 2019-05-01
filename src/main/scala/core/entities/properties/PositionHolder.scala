package core.entities.properties

import core.entities.Entity
import core.parts.position.{Direction, Position}

trait PositionHolder[T <: PositionHolder[T]] extends Entity {
    val position: Position
    
    protected def setPosition(position: Position): T
    
    def enableMovement(): T = {
        setPosition(position.enableMovement())
    }
    
    def disableMovement(): T = {
        setPosition(position.disableMovement())
    }
    
    def enableRotation(): T = {
        setPosition(position.enableRotation())
    }
    
    def disableRotation(): T = {
        setPosition(position.disableRotation())
    }
    
    def moveTo(x: Int = position.coordinates.x, y: Int = position.coordinates.y): T = {
        setPosition(position.moveTo(x, y))
    }
    
    def moveBy(dx: Int = 0, dy: Int = 0): T = {
        setPosition(position.moveTo(dx, dy))
    }
    
    def rotateTo(direction: Direction = position.direction): T = {
        setPosition(position.rotateTo(direction))
    }
    
    def rotate45Clockwise(): T = {
        setPosition(position.rotate45Clockwise())
    }
    
    def rotate90Clockwise(): T = {
        setPosition(position.rotate90Clockwise())
    }
    
    def rotate45Counterclockwise(): T = {
        setPosition(position.rotate45Counterclockwise())
    }
    
    def rotate90Counterclockwise(): T = {
        setPosition(position.rotate90Counterclockwise())
    }
    
    def rotate180(): T = {
        setPosition(position.rotate180())
    }
}
package core.entities.traits.properties

import core.entities.Entity
import core.parts.position.{Direction, Position}

trait PositionHolder extends Entity {
    val position: Position
    
    protected def setPosition(position: Position): PositionHolder
    
    def enableMovement(): PositionHolder = {
        setPosition(position.enableMovement())
    }
    
    def disableMovement(): PositionHolder = {
        setPosition(position.disableMovement())
    }
    
    def enableRotation(): PositionHolder = {
        setPosition(position.enableRotation())
    }
    
    def disableRotation(): PositionHolder = {
        setPosition(position.disableRotation())
    }
    
    def moveTo(x: Int = position.coordinates.x, y: Int = position.coordinates.y): PositionHolder = {
        setPosition(position.moveTo(x, y))
    }
    
    def moveBy(dx: Int = 0, dy: Int = 0): PositionHolder = {
        setPosition(position.moveTo(dx, dy))
    }
    
    def rotateTo(direction: Direction = position.direction): PositionHolder = {
        setPosition(position.rotateTo(direction))
    }
    
    def rotate45Clockwise(): PositionHolder = {
        setPosition(position.rotate45Clockwise())
    }
    
    def rotate90Clockwise(): PositionHolder = {
        setPosition(position.rotate90Clockwise())
    }
    
    def rotate45Counterclockwise(): PositionHolder = {
        setPosition(position.rotate45Counterclockwise())
    }
    
    def rotate90Counterclockwise(): PositionHolder = {
        setPosition(position.rotate90Counterclockwise())
    }
    
    def rotate180(): PositionHolder = {
        setPosition(position.rotate180())
    }
}

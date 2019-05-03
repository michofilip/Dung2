package core.entity2.traits

import core.entity2.Entity2
import core.parts.position.{Direction, Position}

trait PositionHolder2 extends Entity2 {
    val position: Position
    
    protected def setPosition(position: Position): PositionHolder2
    
    def enableMovement(): PositionHolder2 = {
        setPosition(position.enableMovement())
    }
    
    def disableMovement(): PositionHolder2 = {
        setPosition(position.disableMovement())
    }
    
    def enableRotation(): PositionHolder2 = {
        setPosition(position.enableRotation())
    }
    
    def disableRotation(): PositionHolder2 = {
        setPosition(position.disableRotation())
    }
    
    def moveTo(x: Int = position.coordinates.x, y: Int = position.coordinates.y): PositionHolder2 = {
        setPosition(position.moveTo(x, y))
    }
    
    def moveBy(dx: Int = 0, dy: Int = 0): PositionHolder2 = {
        setPosition(position.moveTo(dx, dy))
    }
    
    def rotateTo(direction: Direction = position.direction): PositionHolder2 = {
        setPosition(position.rotateTo(direction))
    }
    
    def rotate45Clockwise(): PositionHolder2 = {
        setPosition(position.rotate45Clockwise())
    }
    
    def rotate90Clockwise(): PositionHolder2 = {
        setPosition(position.rotate90Clockwise())
    }
    
    def rotate45Counterclockwise(): PositionHolder2 = {
        setPosition(position.rotate45Counterclockwise())
    }
    
    def rotate90Counterclockwise(): PositionHolder2 = {
        setPosition(position.rotate90Counterclockwise())
    }
    
    def rotate180(): PositionHolder2 = {
        setPosition(position.rotate180())
    }
}
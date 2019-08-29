package core.entities.traits.properties

import core.entities.Entity
import core.parts.position.{Direction, Position}

trait PositionProperty extends Entity {
    val position: Position
    
    protected def setPosition(position: Position): PositionProperty
    
    def enableMovement(): PositionProperty = setPosition(position.enableMovement())
    
    def disableMovement(): PositionProperty = setPosition(position.disableMovement())
    
    def enableRotation(): PositionProperty = setPosition(position.enableRotation())
    
    def disableRotation(): PositionProperty = setPosition(position.disableRotation())
    
    def moveTo(x: Int = position.coordinates.x, y: Int = position.coordinates.y): PositionProperty =
        setPosition(position.moveTo(x, y))
    
    def moveBy(dx: Int = 0, dy: Int = 0): PositionProperty = setPosition(position.moveTo(dx, dy))
    
    def rotateTo(direction: Direction = position.direction): PositionProperty =
        setPosition(position.rotateTo(direction))
    
    def rotate45Clockwise(): PositionProperty = setPosition(position.rotate45Clockwise())
    
    def rotate90Clockwise(): PositionProperty = setPosition(position.rotate90Clockwise())
    
    def rotate45Counterclockwise(): PositionProperty = setPosition(position.rotate45Counterclockwise())
    
    def rotate90Counterclockwise(): PositionProperty = setPosition(position.rotate90Counterclockwise())
    
    def rotate180(): PositionProperty = setPosition(position.rotate180())
}

package core.entity.properties

import core.entity.Entity
import core.parts.position.{Coordinates, Position}

trait PositionHolder[T <: PhysicsHolder[T]] extends Entity[T] {
    val position: Position
    
    protected def setPosition(position: Position): T
    
    def moveTo(x: Int = position.coordinates.x, y: Int = position.coordinates.y): T = {
        setPosition(Position(Coordinates(x, y), position.direction))
    }
    
    def moveBy(dx: Int = 0, dy: Int = 0): T = {
        moveTo(position.coordinates.x + dx, position.coordinates.y + dy)
    }
    
    def turnClockwise90: T = {
        setPosition(Position(position.coordinates, position.direction.turnClockwise90))
    }
    
    def turnCounterClockwise90: T = {
        setPosition(Position(position.coordinates, position.direction.turnCounterClockwise90))
    }
    
    def turn180: T = {
        setPosition(Position(position.coordinates, position.direction.turn180))
    }
}
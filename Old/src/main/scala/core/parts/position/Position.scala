package core.parts.position

import json.MyJ.jObject
import json.{JSONParsable, JValue}

case class Position(coordinates: Coordinates, direction: Direction, canMove: Boolean, canRotate: Boolean) extends JSONParsable {
    private def update(coordinates: Coordinates = coordinates, direction: Direction = direction,
                       canMove: Boolean = canMove, canRotate: Boolean = canRotate): Position = {
        Position(coordinates, direction, canMove, canRotate)
    }
    
    def enableMovement(): Position = {
        update(canMove = true)
    }
    
    def disableMovement(): Position = {
        update(canMove = false)
    }
    
    def enableRotation(): Position = {
        update(canRotate = true)
    }
    
    def disableRotation(): Position = {
        update(canRotate = false)
    }
    
    def moveTo(x: Int = coordinates.x, y: Int = coordinates.y): Position = {
        if (canMove) {
            update(coordinates = coordinates.moveTo(x, y))
        } else this
    }
    
    def moveBy(dx: Int = 0, dy: Int = 0): Position = {
        if (canMove) {
            update(coordinates = coordinates.moveBy(dx, dy))
        } else this
    }
    
    def rotateTo(direction: Direction = direction): Position = {
        if (canRotate) {
            update(direction = direction)
        } else this
    }
    
    def rotate45Clockwise(): Position = {
        if (canRotate) {
            update(direction = direction.next)
        } else this
    }
    
    def rotate90Clockwise(): Position = {
        if (canRotate) {
            update(direction = direction.next.next)
        } else this
    }
    
    def rotate45Counterclockwise(): Position = {
        if (canRotate) {
            update(direction = direction.previous)
        } else this
    }
    
    def rotate90Counterclockwise(): Position = {
        if (canRotate) {
            update(direction = direction.previous.previous)
        } else this
    }
    
    def rotate180(): Position = {
        if (canRotate) {
            update(direction = direction.opposite)
        } else this
    }
    
    override def toJSON: JValue = {
        jObject(
            "coordinates" -> coordinates,
            "canMove" -> canMove,
            "direction" -> direction,
            "canRotate" -> canRotate
        )
    }
}

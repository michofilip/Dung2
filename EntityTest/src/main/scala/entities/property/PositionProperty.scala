package entities.property

import entities.Entity
import parts.Position

trait PositionProperty extends Entity {
    val position: Position
    val canMove: Boolean
    
    def setPosition(position: Position): PositionProperty
    
    def setCanMove(canMove: Boolean): PositionProperty
}

package entities.templates

import entities.Entity
import entities.property.PositionProperty
import parts.Position

final class Character(override val id: Int) extends Entity with PositionProperty{
    override val position: Position = _
    override val canMove: Boolean = _
    
    override def setPosition(position: Position): PositionProperty = ???
    
    override def setCanMove(canMove: Boolean): PositionProperty = ???
}
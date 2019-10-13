package entity

import entity.parts.{Physics, Position}

class Player(_id: Int, _position: Position, _physics: Physics) extends Entity with PositionProperty with PhysicsProperty {
    override val id: Int = _id
    override val position: Position = _position
    override val physics: Physics = _physics
    
    def update(id: Int = id, position: Position = position, physics: Physics = physics): Player =
        new Player(id, position, physics)
}

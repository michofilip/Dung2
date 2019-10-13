package entity

import entity.parts.Position

abstract class Entity {
    val id: Int
}

object Entity {
    def moveTo(entity: Entity, x: Int, y: Int): Entity = entity match {
        case en: Player => en.update(position = Position(x, y))
        case _ => entity
    }
    
    def moveBy(entity: Entity, dx: Int, dy: Int): Entity = entity match {
        case en: Player => en.update(position = Position(en.position.x + dx, en.position.y + dy))
        case _ => entity
    }
}

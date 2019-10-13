package entity2

import entity2.parts2.{Animation, Coordinates, Physics, Position}

object EntityProcessor {
    
    implicit class PositionProcessor(entity: Entity) {
        def setPosition(position: Position): Entity = entity.copy(position = Some(position))
        
        def removePosition(): Entity = entity.copy(position = None)
        
        def moveTo(x: Int, y: Int): Entity = entity.position match {
            case Some(position) if position.canMove => setPosition(position.copy(coordinates = Coordinates(x, y)))
            case _ => entity
        }
        
        def moveBy(dx: Int, dy: Int): Entity = entity.position match {
            case Some(position@Position(Coordinates(x, y), _, canMove, _)) if canMove =>
                setPosition(position.copy(coordinates = Coordinates(x + dx, y + dy)))
            case _ => entity
        }
        
        def rotateTo(direction: Int): Entity = entity.position match {
            case Some(position) if position.canRotate => setPosition(position.copy(direction = direction))
            case _ => entity
        }
        
        def rotateBy(angle: Int): Entity = entity.position match {
            case Some(position@Position(_, direction, _, canRotate)) if canRotate =>
                setPosition(position.copy(direction = direction + angle))
            case _ => entity
        }
    }
    
    implicit class PhysicsProcessor(entity: Entity) {
        def setPhysics(physics: Physics): Entity = entity.copy(physics = Some(physics))
        
        def removePhysics(): Entity = entity.copy(physics = None)
        
        def setSolid(solid: Boolean): Entity = entity.physics match {
            case Some(physics) => setPhysics(physics.copy(solid = solid))
            case _ => entity
        }
        
        def setOpaque(opaque: Boolean): Entity = entity.physics match {
            case Some(physics) => setPhysics(physics.copy(opaque = opaque))
            case _ => entity
        }
    }
    
    implicit class AnimationProcessor(entity: Entity) {
        def setAnimation(animation: Animation): Entity = entity.copy(animation = Some(animation))
        
        def removeAnimation(): Entity = entity.copy(animation = None)
    }
    
}

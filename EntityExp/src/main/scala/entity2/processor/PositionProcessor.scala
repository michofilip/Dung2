//package entity2.processor
//
//import entity2.Entity
//import entity2.parts2.{Coordinates, Position}
//
//object PositionProcessor {
//    def setPosition(entity: Entity, position: Position): Entity = entity.copy(positionOpt = Some(position))
//
//    def removePosition(entity: Entity): Entity = entity.copy(positionOpt = None)
//
//    def moveTo(entity: Entity, x: Int, y: Int): Entity = entity.positionOpt match {
//        case Some(position) if position.canMove => setPosition(entity, position.copy(coordinates = Coordinates(x, y)))
//        case _ => entity
//    }
//
//    def moveBy(entity: Entity, dx: Int, dy: Int): Entity = entity.positionOpt match {
//        case Some(position@Position(Coordinates(x, y), _, canMove, _)) if canMove =>
//            setPosition(entity, position.copy(coordinates = Coordinates(x + dx, y + dy)))
//        case _ => entity
//    }
//}

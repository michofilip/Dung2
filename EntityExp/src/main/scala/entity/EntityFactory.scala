package entity

import entity.EntityMutator._
import entity.parts.{Category, Coordinates, Direction, Position, State}
import entity.selectors.{GraphicsSelector, PhysicsSelector}

class EntityFactory(implicit physicsSelector: PhysicsSelector, graphicsSelector: GraphicsSelector) {
    def makeSwitch(): Entity = {
        Entity(id = 1, category = Category.Switch)
                .setState(State.Off)
                .setPosition(Position(Coordinates(10, 25), Direction.North, canMove = false, canRotate = false))
                .selectPhysics()
                .selectGraphics()
    }
}

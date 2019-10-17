package entity

import entity.EntityMutator._
import entity.parts.Category._
import entity.parts.State._
import entity.parts.{Coordinates, Direction, Position}
import entity.selectors.{AnimationSelector, PhysicsSelector}

class EntityFactory(implicit physicsSelector: PhysicsSelector, graphicsSelector: AnimationSelector) {
    def makeSwitch(): Entity =
        Entity(id = 1, category = Switch, timestamp = 0)
                .setState(Off)
                .setPosition(Position(Coordinates(10, 25), Direction.North, canMove = false, canRotate = false))
                .selectPhysics()
                .selectGraphics()
}

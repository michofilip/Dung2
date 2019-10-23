package entity

import entity.EntityMutator._
import parts.Category._
import parts.Position
import parts.State._
import selectors.{AnimationSelector, PhysicsSelector}

class EntityFactory(implicit
                    physicsSelector: PhysicsSelector,
                    graphicsSelector: AnimationSelector) {
    
    def makeFloor(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Floor)
                .setPosition(Position(x, y))
                .selectPhysics()
                .selectGraphics()
    
    def makeWall(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Wall)
                .setPosition(Position(x, y))
                .selectPhysics()
                .selectGraphics()
    
    def makeSwitch(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Switch)
                .setState(Off)
                .setPosition(Position(x, y))
                .selectPhysics()
                .selectGraphics()
    
    def makeDoor(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Door)
                .setState(Open)
                .setPosition(Position(x, y))
                .selectPhysics()
                .selectGraphics()
}

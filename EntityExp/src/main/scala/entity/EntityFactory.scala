package entity

import entity.EntityMutator._
import parts.Category._
import parts.Coordinates
import parts.State._
import selectors.{AnimationSelector, PhysicsSelector}

class EntityFactory(implicit
                    physicsSelector: PhysicsSelector,
                    graphicsSelector: AnimationSelector) {
    
    def makeFloor(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Floor, timestamp = 0)
                .setCoordinates(Coordinates(x, y))
                .selectPhysics()
                .selectAnimation()
    
    def makeWall(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Wall, timestamp = 0)
                .setCoordinates(Coordinates(x, y))
                .selectPhysics()
                .selectAnimation()
    
    def makeSwitch(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Switch, timestamp = 0)
                .setState(Off)
                .setCoordinates(Coordinates(x, y))
                .selectPhysics()
                .selectAnimation()
    
    def makeDoor(id: Int, x: Int, y: Int): Entity =
        Entity(id = id, category = Door, timestamp = 0)
                .setState(Open)
                .setCoordinates(Coordinates(x, y))
                .selectPhysics()
                .selectAnimation()
}

package entity

import entity.EntityMutator._
import entity.parts.Category._
import entity.parts.Coordinates
import entity.parts.State._
import entity.selectors.{AnimationSelector, PhysicsSelector}

class EntityFactory(implicit
                    physicsSelector: PhysicsSelector,
                    graphicsSelector: AnimationSelector) {
    def makeSwitch(): Entity =
        Entity(id = 1, category = Switch, timestamp = 0)
                .setState(Off)
                .setCoordinates(Coordinates(10, 25))
                .selectPhysics()
                .selectAnimation()
}

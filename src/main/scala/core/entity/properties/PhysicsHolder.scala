package core.entity.properties

import core.entity.Entity
import core.entity.properties.physics.Physics
import core.entity.selectors.PhysicsSelector

trait PhysicsHolder extends Entity {
    protected val physicsSelector: PhysicsSelector
    
    def physicsSelectorId: String = {
        physicsSelector.id
    }
    
    def physics: Physics = {
        val stateOpt = this match {
            case en: StateHolder => Some(en.state)
            case _ => None
        }
        physicsSelector.getPhysics(stateOpt)
    }
}
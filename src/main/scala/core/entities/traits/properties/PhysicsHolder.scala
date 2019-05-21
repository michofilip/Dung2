package core.entities.traits.properties

import core.entities.Entity
import core.parts.physics.{Physics, PhysicsSelector}

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

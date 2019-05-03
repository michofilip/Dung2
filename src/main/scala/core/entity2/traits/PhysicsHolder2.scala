package core.entity2.traits

import core.entities.selectors.PhysicsSelector
import core.entity2.Entity2
import core.parts.physics.Physics

trait PhysicsHolder2 extends Entity2 {
    protected val physicsSelector: PhysicsSelector
    
    def physicsSelectorId: String = {
        physicsSelector.id
    }
    
    def physics: Physics = {
        val stateOpt = this match {
            case en: StateHolder2 => Some(en.state)
            case _ => None
        }
        physicsSelector.getPhysics(stateOpt)
    }
}
package core.entity2.traits.properties

import core.entity2.Entity2
import core.parts.physics.{Physics, PhysicsSelector2}

trait PhysicsHolder2 extends Entity2 {
    protected val physicsSelector: PhysicsSelector2
    
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

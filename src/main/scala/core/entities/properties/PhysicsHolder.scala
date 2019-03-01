package core.entities.properties

import core.entities.Entity
import core.entities.selectors.PhysicsSelector
import core.parts.physics.Physics

trait PhysicsHolder[T <: PhysicsHolder[T]] extends Entity[T] {
    protected val physicsSelector: PhysicsSelector
    
    def physicsSelectorId: String = {
        physicsSelector.id
    }
    
    def physics: Physics = {
        val stateOpt = this match {
            case en: StateHolder[_] => Some(en.state)
            case _ => None
        }
        physicsSelector.getPhysics(stateOpt)
    }
}
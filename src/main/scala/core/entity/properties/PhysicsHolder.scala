package core.entity.properties

import core.entity.Entity
import core.entity.selectors.PhysicsSelector
import core.parts.physics.Physics

trait PhysicsHolder[T <: PhysicsHolder[T]] extends Entity[T] {
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
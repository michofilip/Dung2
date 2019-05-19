//package core.entities.properties
//
//import core.entities.Entity
//import core.entities.selectors.PhysicsSelector2
//import core.parts.physics.Physics
//
//trait PhysicsHolder[T <: PhysicsHolder[T]] extends Entity {
//    protected val physicsSelector: PhysicsSelector2
//
//    def physicsSelectorId: String = {
//        physicsSelector.id
//    }
//
//    def physics: Physics = {
//        val stateOpt = this match {
//            case en: StateHolder[_] => Some(en.state)
//            case _ => None
//        }
//        physicsSelector.getPhysics(stateOpt)
//    }
//}
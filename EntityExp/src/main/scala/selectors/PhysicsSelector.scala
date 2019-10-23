package selectors

import parts.Category._
import parts.State._
import parts.{Category, Physics, State}

class PhysicsSelector {
    
    val physicsFF = Physics(solid = false, opaque = false)
    val physicsFT = Physics(solid = false, opaque = true)
    val physicsTF = Physics(solid = true, opaque = false)
    val physicsTT = Physics(solid = true, opaque = true)
    
    def select(category: Category, stateOpt: Option[State]): Option[Physics] = (category, stateOpt) match {
        case (Floor, None) => Some(physicsFF)
        
        case (Wall, None) => Some(physicsTT)
        
        case (Switch, Some(Off)) => Some(physicsTF)
        case (Switch, Some(SwitchingOff)) => Some(physicsTF)
        case (Switch, Some(SwitchingOn)) => Some(physicsTF)
        case (Switch, Some(On)) => Some(physicsTF)
        
        case (Door, Some(Open)) => Some(physicsFF)
        case (Door, Some(Opening)) => Some(physicsTF)
        case (Door, Some(Closing)) => Some(physicsTF)
        case (Door, Some(Close)) => Some(physicsTT)
        case (Door, Some(Unlocking)) => Some(physicsTT)
        case (Door, Some(Locking)) => Some(physicsTT)
        case (Door, Some(Locked)) => Some(physicsTT)
        
        case _ => None
    }
    
}

package core.parts.physics

import core.parts.state.State
import core.parts.state.State._

sealed abstract class PhysicsSelector {
    val id: String
    
    def getPhysics(stateOpt: Option[State]): Physics
}


object PhysicsSelector {
    
    case object LeverPhysicsSelector extends PhysicsSelector {
        override val id: String = "LeverPhysicsSelector"
        
        override def getPhysics(stateOpt: Option[State]): Physics = {
            val offPhysics = Physics(solid = true, opaque = false)
            val switchingOffPhysics = Physics(solid = true, opaque = false)
            val switchingOnPhysics = Physics(solid = true, opaque = false)
            val onPhysics = Physics(solid = true, opaque = false)
            
            stateOpt match {
                case Some(Off) => offPhysics
                case Some(SwitchingOff) => switchingOffPhysics
                case Some(SwitchingOn) => switchingOnPhysics
                case Some(On) => onPhysics
                case _ => offPhysics
            }
        }
    }
    
    case object DoorPhysicsSelector extends PhysicsSelector {
        override val id: String = "DoorPhysicsSelector"
        
        override def getPhysics(stateOpt: Option[State]): Physics = {
            val openPhysics = Physics(solid = false, opaque = false)
            val openingOffPhysics = Physics(solid = true, opaque = false)
            val closingPhysics = Physics(solid = true, opaque = false)
            val closePhysics = Physics(solid = true, opaque = true)
            val unlockingPhysics = Physics(solid = true, opaque = true)
            val lockingPhysics = Physics(solid = true, opaque = true)
            val lockedPhysics = Physics(solid = true, opaque = true)
            
            stateOpt match {
                case Some(Open) => openPhysics
                case Some(Opening) => openingOffPhysics
                case Some(Closing) => closingPhysics
                case Some(Close) => closePhysics
                case Some(Unlocking) => unlockingPhysics
                case Some(Locking) => lockingPhysics
                case Some(Locked) => lockedPhysics
                case _ => openPhysics
            }
        }
    }
    
}

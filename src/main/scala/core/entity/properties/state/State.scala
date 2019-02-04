package core.entity.properties.state

sealed abstract class State

object State {
    
    // Switches
    sealed trait SwitchableState extends State
    
    case object Off extends State with SwitchableState
    
    case object SwitchingOff extends State with SwitchableState
    
    case object SwitchingOn extends State with SwitchableState
    
    case object On extends State with SwitchableState
    
    // Doors & Containers
    sealed trait OpenableState extends State
    
    case object Open extends State with OpenableState
    
    case object Opening extends State with OpenableState
    
    case object Closing extends State with OpenableState
    
    case object Close extends State with OpenableState
    
    case object Unlocking extends State with OpenableState
    
    case object Locking extends State with OpenableState
    
    case object Locked extends State with OpenableState
    
    // Characters
    sealed trait CharacterState extends State
    
    case object Standing extends State with CharacterState
    
    case object Walking extends State with CharacterState
    
}

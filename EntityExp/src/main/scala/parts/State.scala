package parts

sealed abstract class State

object State {
    
    case object Off extends State
    
    case object SwitchingOff extends State
    
    case object SwitchingOn extends State
    
    case object On extends State
    
    case object Open extends State
    
    case object Opening extends State
    
    case object Closing extends State
    
    case object Close extends State
    
    case object Unlocking extends State
    
    case object Locking extends State
    
    case object Locked extends State
    
    
}

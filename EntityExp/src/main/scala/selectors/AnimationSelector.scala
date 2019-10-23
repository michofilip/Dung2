package selectors

import parts.Animation.SingleFrameAnimation
import parts.Category._
import parts.State.{Close, Closing, Locked, Locking, Off, On, Open, Opening, SwitchingOff, SwitchingOn, Unlocking}
import parts.{Animation, Category, Direction, Frame, State}

class AnimationSelector {
    
    val animation = SingleFrameAnimation(frame = Frame(id = 1, layer = 0))
    val animation2 = SingleFrameAnimation(frame = Frame(id = 2, layer = 0))
    
    def select(category: Category, stateOpt: Option[State], directionOpt: Option[Direction]): Option[Animation] =
        (category, stateOpt, directionOpt) match {
            case (Floor, None, None) => Some(animation)
            
            case (Wall, None, None) => Some(animation)
            
            case (Switch, Some(Off), None) => Some(animation)
            case (Switch, Some(SwitchingOff), None) => Some(animation)
            case (Switch, Some(SwitchingOn), None) => Some(animation2)
            case (Switch, Some(On), None) => Some(animation)
            
            case (Door, Some(Open), None) => Some(animation)
            case (Door, Some(Opening), None) => Some(animation)
            case (Door, Some(Closing), None) => Some(animation)
            case (Door, Some(Close), None) => Some(animation)
            case (Door, Some(Unlocking), None) => Some(animation)
            case (Door, Some(Locking), None) => Some(animation)
            case (Door, Some(Locked), None) => Some(animation)
            
            case _ => None
        }
}

package entity.selectors

import entity.parts.Animation.SingleFrameAnimation
import entity.parts.Category._
import entity.parts.State.{Close, Closing, Locked, Locking, Off, On, Open, Opening, SwitchingOff, SwitchingOn, Unlocking}
import entity.parts.{Animation, Category, Direction, Frame, State}

class AnimationSelector {
    
    val animation = SingleFrameAnimation(Frame(1))
    val animation2 = SingleFrameAnimation(Frame(2))
    
    def select(category: Category, state: Option[State], direction: Option[Direction]): Option[Animation] =
        (category, state, direction) match {
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

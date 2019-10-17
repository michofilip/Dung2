package entity.selectors

import entity.parts.Category._
import entity.parts.State.{Close, Closing, Locked, Locking, Off, On, Open, Opening, SwitchingOff, SwitchingOn, Unlocking}
import entity.parts.{Animation, Category, Frame, Position, State}

class AnimationSelector {
    
    val animation = Animation(Vector(Frame(1)), duration = 1000, isLooped = true)
    val animation2 = Animation(Vector(Frame(2)), duration = 1000, isLooped = true)
    
    def select(category: Category, state: Option[State], position: Option[Position]): Option[Animation] =
        (category, state, position.map(_.direction)) match {
            case (Floor, None, _) => Some(animation)
            
            case (Wall, None, _) => Some(animation)
            
            case (Switch, Some(Off), _) => Some(animation)
            case (Switch, Some(SwitchingOff), _) => Some(animation)
            case (Switch, Some(SwitchingOn), _) => Some(animation2)
            case (Switch, Some(On), _) => Some(animation)
            
            case (Door, Some(Open), _) => Some(animation)
            case (Door, Some(Opening), _) => Some(animation)
            case (Door, Some(Closing), _) => Some(animation)
            case (Door, Some(Close), _) => Some(animation)
            case (Door, Some(Unlocking), _) => Some(animation)
            case (Door, Some(Locking), _) => Some(animation)
            case (Door, Some(Locked), _) => Some(animation)
            
            case _ => None
        }
}

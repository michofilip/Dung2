package entity.selectors

import entity.parts.Category._
import entity.parts.State.{Close, Closing, Locked, Locking, Off, On, Open, Opening, SwitchingOff, SwitchingOn, Unlocking}
import entity.parts.{Category, Direction, Graphics, State}

class GraphicsSelector {
    
    val graphics = Graphics()
    
    def select(category: Category, state: Option[State], direction: Option[Direction]): Option[Graphics] =
        (category, state, direction) match {
            case (Floor, None, _) => Some(graphics)
            
            case (Wall, None, _) => Some(graphics)
            
            case (Switch, Some(Off), _) => Some(graphics)
            case (Switch, Some(SwitchingOff), _) => Some(graphics)
            case (Switch, Some(SwitchingOn), _) => Some(graphics)
            case (Switch, Some(On), _) => Some(graphics)
            
            case (Door, Some(Open), _) => Some(graphics)
            case (Door, Some(Opening), _) => Some(graphics)
            case (Door, Some(Closing), _) => Some(graphics)
            case (Door, Some(Close), _) => Some(graphics)
            case (Door, Some(Unlocking), _) => Some(graphics)
            case (Door, Some(Locking), _) => Some(graphics)
            case (Door, Some(Locked), _) => Some(graphics)
            
            case _ => None
        }
}

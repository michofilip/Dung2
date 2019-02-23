package core.entity

import core.entity.properties.state.State._
import core.entity.properties.StateHolder

abstract class Switchable extends MapEntity with StateHolder {
    def setSwitchableState(switchableState: SwitchableState, timeStamp: Long): T = {
        (state, switchableState) match {
            case (Off, SwitchingOn) => setState(switchableState, timeStamp)
            case (SwitchingOn, On) => setState(switchableState, timeStamp)
            case (On, SwitchingOff) => setState(switchableState, timeStamp)
            case (SwitchingOff, Off) => setState(switchableState, timeStamp)
            
            case _ => setState(state, this.timeStamp)
        }
    }
}
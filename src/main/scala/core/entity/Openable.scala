package core.entity

import core.entity.properties.state.State._
import core.entity.traits.StateHolder

abstract class Openable extends MapEntity with StateHolder {
    val lockCode: Long
    
    def setOpenableState(openableState: OpenableState, timeStamp: Long): T = {
        (state, openableState) match {
            case (Open, Closing) => setState(openableState, timeStamp)
            case (Closing, Close) => setState(openableState, timeStamp)
            case (Close, Opening) => setState(openableState, timeStamp)
            case (Opening, Open) => setState(openableState, timeStamp)
            
            case (Close, Locking) => setState(openableState, timeStamp)
            case (Locking, Locked) => setState(openableState, timeStamp)
            case (Locked, Unlocking) => setState(openableState, timeStamp)
            case (Unlocking, Close) => setState(openableState, timeStamp)
            
            case _ => setState(state, this.timeStamp)
        }
    }
}
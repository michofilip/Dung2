package core.entity2.traits.templates

import core.entity2.Entity2
import core.entity2.traits.properties.StateHolder2
import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait SwitchingCapable extends SimpleEntity with StateHolder2 {
    val switchingOffDuration: Int
    val switchingOnDuration: Int
    
    def beginSwitchingOff(timeStamp: TimeStamp): SwitchingCapable = {
        setState(SwitchingOff).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishSwitchingOff(timeStamp: TimeStamp): SwitchingCapable = {
        setState(Off).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def beginSwitchingOn(timeStamp: TimeStamp): SwitchingCapable = {
        setState(SwitchingOn).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishSwitchingOn(timeStamp: TimeStamp): SwitchingCapable = {
        setState(On).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    implicit private def toSwitchCapable(entity: Entity2): SwitchingCapable = {
        entity.asInstanceOf[SwitchingCapable]
    }
}

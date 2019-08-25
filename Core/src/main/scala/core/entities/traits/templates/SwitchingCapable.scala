package core.entities.traits.templates

import core.entities.Entity
import core.entities.traits.properties.StateHolder
import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait SwitchingCapable extends SimpleEntity with StateHolder {
    val switchingOffDuration: Long = animationSelector.getAnimation(Some(SwitchingOff), Some(position.direction)).duration
    val switchingOnDuration: Long = animationSelector.getAnimation(Some(SwitchingOn), Some(position.direction)).duration
    
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
    
    implicit private def toSwitchCapable(entity: Entity): SwitchingCapable = {
        entity.asInstanceOf[SwitchingCapable]
    }
}

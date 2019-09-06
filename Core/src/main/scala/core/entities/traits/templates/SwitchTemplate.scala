package core.entities.traits.templates

import core.entities.Entity
import core.entities.traits.properties.StateProperty
import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait SwitchTemplate extends SimpleEntity with StateProperty {
    implicit private def toSwitchCapable(entity: Entity): SwitchTemplate =
        entity.asInstanceOf[SwitchTemplate]
    
    val switchingOffDuration: Long = animationSelector.getAnimation(Some(SwitchingOff), Some(position.direction)).duration
    val switchingOnDuration: Long = animationSelector.getAnimation(Some(SwitchingOn), Some(position.direction)).duration
    
    def beginSwitchingOff(timeStamp: TimeStamp): StateProperty =
        if (state == On) setState(SwitchingOff).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishSwitchingOff(timeStamp: TimeStamp): StateProperty =
        if (state == SwitchingOff) setState(Off).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginSwitchingOn(timeStamp: TimeStamp): StateProperty =
        if (state == Off) setState(SwitchingOn).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishSwitchingOn(timeStamp: TimeStamp): StateProperty =
        if (state == SwitchingOn) setState(On).setAnimationBeginningTimeStamp(timeStamp)
        else this
}

package core.entities.templates

import core.entities.properties.StateHolder
import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}

abstract class Switchable[T <: Switchable[T]] extends MapEntity[T] with StateHolder[T] {
    
    val switchingOffLength = 1000
    val switchingOnLength = 1000
    
    def beginSwitchingOff(animationStartTime: Long): T = {
        setState(SwitchingOff)
//                .setAnimationStartTime(animationStartTime)
    }
    
    def finishSwitchingOff(animationStartTime: Long): T = {
        setState(Off)
//                .setAnimationStartTime(animationStartTime)
    }
    
    def beginSwitchingOn(animationStartTime: Long): T = {
        setState(SwitchingOn)
//                .setAnimationStartTime(animationStartTime)
    }
    
    def finishSwitchingOn(animationStartTime: Long): T = {
        setState(On)
//                .setAnimationStartTime(animationStartTime)
    }
}

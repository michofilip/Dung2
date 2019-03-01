package core.entity

import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}
import core.entity.properties.{AnimationHolder, PhysicsHolder, PositionHolder, StateHolder}

abstract class Switchable extends Entity with PositionHolder with PhysicsHolder with AnimationHolder with StateHolder {
    override protected type T = Switchable
    
    def beginSwitchingOff(time: Long): T = {
        setState(SwitchingOff).setAnimationStartTime(time)
    }
    
    def finishSwitchingOff(time: Long): T = {
        setState(Off).setAnimationStartTime(time)
    }
    
    def beginSwitchingOn(time: Long): T = {
        setState(SwitchingOn).setAnimationStartTime(time)
    }
    
    def finishSwitchingOn(time: Long): T = {
        setState(On).setAnimationStartTime(time)
    }
}

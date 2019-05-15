package core.entity2.traits

import core.entity2.Entity2
import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}

import scala.language.implicitConversions

trait SwitchingCapable extends Entity2 with StateHolder2 {
    val switchingOffDuration: Int
    val switchingOnDuration: Int
    
    def beginSwitchingOff(): SwitchingCapable = {
        setState(SwitchingOff)
    }
    
    def finishSwitchingOff(): SwitchingCapable = {
        setState(Off)
    }
    
    def beginSwitchingOn(): SwitchingCapable = {
        setState(SwitchingOn)
    }
    
    def finishSwitchingOn(): SwitchingCapable = {
        setState(On)
    }
    
    implicit private def toSwitchCapable(entity: Entity2): SwitchingCapable = {
        entity.asInstanceOf[SwitchingCapable]
    }
}
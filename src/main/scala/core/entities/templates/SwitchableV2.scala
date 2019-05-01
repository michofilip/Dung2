package core.entities.templates

import core.entities.Entity
import core.entities.properties.StateHolder
import core.entities.templates.SwitchableV2.entityToSwitchable
import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}

import scala.language.implicitConversions

abstract class SwitchableV2 extends MapEntity[Entity] with StateHolder[Entity] {
    
    val switchingOffLength = 1000
    val switchingOnLength = 1000
    
    def beginSwitchingOff(animationStartTime: Long): SwitchableV2 = {
        setState(SwitchingOff).setAnimationStartTime(animationStartTime)
    }
    
    def finishSwitchingOff(animationStartTime: Long): SwitchableV2 = {
        setState(Off).setAnimationStartTime(animationStartTime)
    }
    
    def beginSwitchingOn(animationStartTime: Long): SwitchableV2 = {
        setState(SwitchingOn).setAnimationStartTime(animationStartTime)
    }
    
    def finishSwitchingOn(animationStartTime: Long): SwitchableV2 = {
        setState(On).setAnimationStartTime(animationStartTime)
    }
}

object SwitchableV2 {
    implicit private def entityToSwitchable(entity: Entity): SwitchableV2 = {
        entity.asInstanceOf[SwitchableV2]
    }
}

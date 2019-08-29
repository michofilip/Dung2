package core.entities.traits.templates

import core.entities.Entity
import core.entities.traits.properties.StateProperty
import core.parts.state.State.{Close, Closing, Open, Opening}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait ClosingCapable extends SimpleEntity with StateProperty {
    
    val openingDuration: Long = animationSelector.getAnimation(Some(Opening), Some(position.direction)).duration
    val closingDuration: Long = animationSelector.getAnimation(Some(Closing), Some(position.direction)).duration
    
    def beginOpening(timeStamp: TimeStamp): ClosingCapable = {
        setState(Opening).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishOpening(timeStamp: TimeStamp): ClosingCapable = {
        setState(Open).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def beginClosing(timeStamp: TimeStamp): ClosingCapable = {
        setState(Closing).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishClosing(timeStamp: TimeStamp): ClosingCapable = {
        setState(Close).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    implicit private def toClosingCapable(entity: Entity): ClosingCapable = {
        entity.asInstanceOf[ClosingCapable]
    }
}

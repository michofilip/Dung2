package core.entity2.traits.templates

import core.entity2.Entity2
import core.entity2.traits.properties.StateHolder2
import core.parts.state.State.{Close, Closing, Open, Opening}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait ClosingCapable extends SimpleEntity with StateHolder2 {
    
    val openingDuration: Int
    val closingDuration: Int
    
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
    
    implicit private def toClosingCapable(entity: Entity2): ClosingCapable = {
        entity.asInstanceOf[ClosingCapable]
    }
}

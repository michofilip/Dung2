package core.entities.traits.templates

import core.entities.Entity
import core.entities.traits.properties.StateProperty
import core.parts.state.State.{Close, Closing, Locked, Locking, Open, Opening, Unlocking}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait ClosableTemplate extends SimpleEntity with StateProperty {
    implicit private def toClosingCapable(entity: Entity): ClosableTemplate =
        entity.asInstanceOf[ClosableTemplate]
    
    val lockCode: Long
    val openingDuration: Long = animationSelector.getAnimation(Some(Opening), Some(position.direction)).duration
    val closingDuration: Long = animationSelector.getAnimation(Some(Closing), Some(position.direction)).duration
    val unlockingDuration: Long = animationSelector.getAnimation(Some(Unlocking), Some(position.direction)).duration
    val lockingDuration: Long = animationSelector.getAnimation(Some(Locking), Some(position.direction)).duration
    
    def beginOpening(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Close) setState(Opening).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishOpening(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Opening) setState(Open).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginClosing(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Open) setState(Closing).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishClosing(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Closing) setState(Close).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginUnlocking(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Locked) setState(Unlocking).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishUnlocking(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Unlocking) setState(Close).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginLocking(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Close) setState(Locking).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishLocking(timeStamp: TimeStamp): ClosableTemplate =
        if (state == Locking) setState(Locked).setAnimationBeginningTimeStamp(timeStamp)
        else this
}

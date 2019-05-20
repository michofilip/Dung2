package core.entity2.traits.templates

import core.entity2.Entity2
import core.entity2.traits.properties.StateHolder2
import core.parts.state.State.{Close, Closing, Locked, Locking}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait LockingCapable extends SimpleEntity with StateHolder2 {
    val lockCode: Long
    
    val unlockingDuration: Int
    val lockingDuration: Int
    
    def beginUnlocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Closing).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishUnlocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Close).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def beginLocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Locking).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishLocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Locked).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    implicit private def toLockingCapable(entity: Entity2): LockingCapable = {
        entity.asInstanceOf[LockingCapable]
    }
}

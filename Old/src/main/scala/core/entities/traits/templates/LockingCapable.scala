package core.entities.traits.templates

import core.entities.Entity
import core.entities.traits.properties.StateHolder
import core.parts.state.State.{Locked, Locking, Unlocked, Unlocking}
import core.parts.timer.TimeStamp

import scala.language.implicitConversions

trait LockingCapable extends SimpleEntity with StateHolder {
    val lockCode: Long
    
    val unlockingDuration: Long = animationSelector.getAnimation(Some(Unlocking), Some(position.direction)).duration
    val lockingDuration: Long = animationSelector.getAnimation(Some(Locking), Some(position.direction)).duration
    
    def beginUnlocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Unlocking).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishUnlocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Unlocked).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def beginLocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Locking).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    def finishLocking(timeStamp: TimeStamp): LockingCapable = {
        setState(Locked).setAnimationBeginningTimeStamp(timeStamp)
    }
    
    implicit private def toLockingCapable(entity: Entity): LockingCapable = {
        entity.asInstanceOf[LockingCapable]
    }
}

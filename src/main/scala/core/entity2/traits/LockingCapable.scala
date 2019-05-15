package core.entity2.traits

import core.entity2.Entity2
import core.parts.state.State._

import scala.language.implicitConversions

trait LockingCapable extends Entity2 with StateHolder2 {
    val lockCode: Long
    
    val unlockingDuration: Int
    val lockingDuration: Int
    
    def beginUnlocking(): LockingCapable = {
        setState(Closing)
    }
    
    def finishUnlocking(): LockingCapable = {
        setState(Close)
    }
    
    def beginLocking(): LockingCapable = {
        setState(Locking)
    }
    
    def finishLocking(): LockingCapable = {
        setState(Locked)
    }
    
    implicit private def toLockingCapable(entity: Entity2): LockingCapable = {
        entity.asInstanceOf[LockingCapable]
    }
}
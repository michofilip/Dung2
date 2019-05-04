package core.entity2.traits

import core.entity2.Entity2
import core.parts.state.State._

import scala.language.implicitConversions

trait LockingCapable extends Entity2 with StateHolder2 {
    val lockCode: Long
    
//    val openingDuration: Int
//    val closingDuration: Int
    val unlockingDuration: Int
    val lockingDuration: Int
    
//    def beginOpening(): LockingCapable = {
//        setState(Opening)
//    }
//
//    def finishOpening(): LockingCapable = {
//        setState(Open)
//    }
//
//    def beginClosing(): LockingCapable = {
//        setState(Closing)
//    }
//
//    def finishClosing(): LockingCapable = {
//        setState(Close)
//    }
    
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
}

object LockingCapable {
    implicit private def toLockingCapable(entity: Entity2): LockingCapable = {
        entity.asInstanceOf[LockingCapable]
    }
}



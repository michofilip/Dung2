package core.entity2.traits

import core.entity2.Entity2
import core.parts.state.State._

import scala.language.implicitConversions

trait ClosingCapable extends Entity2 with StateHolder2 {
//    val lockCode: Long
    
    val openingDuration: Int
    val closingDuration: Int
//    val unlockingDuration: Int
//    val lockingDuration: Int
    
    def beginOpening(): ClosingCapable = {
        setState(Opening)
    }
    
    def finishOpening(): ClosingCapable = {
        setState(Open)
    }
    
    def beginClosing(): ClosingCapable = {
        setState(Closing)
    }
    
    def finishClosing(): ClosingCapable = {
        setState(Close)
    }
    
//    def beginUnlocking(): ClosingCapable = {
//        setState(Closing)
//    }
//
//    def finishUnlocking(): ClosingCapable = {
//        setState(Close)
//    }
//
//    def beginLocking(): ClosingCapable = {
//        setState(Locking)
//    }
//
//    def finishLocking(): ClosingCapable = {
//        setState(Locked)
//    }
}

object ClosingCapable {
    implicit private def toClosingCapable(entity: Entity2): ClosingCapable = {
        entity.asInstanceOf[ClosingCapable]
    }
}

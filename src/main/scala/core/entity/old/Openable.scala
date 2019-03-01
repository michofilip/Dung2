//package core.entity
//
//import core.entity.properties.StateHolder
//import core.parts.state.State._
//
//abstract class Openable extends MapEntity with StateHolder {
//    val lockCode: Long
//
//    val openingLength = 1000
//    val closingLength = 1000
//    val unlockingLength = 1000
//    val lockingLength = 1000
//
//    def beginOpening(timeStamp: Long): T = {
//        setState(Opening, timeStamp)
//    }
//
//    def finishOpening(timeStamp: Long): T = {
//        setState(Open, timeStamp)
//    }
//
//    def beginClosing(timeStamp: Long): T = {
//        setState(Closing, timeStamp)
//    }
//
//    def finishClosing(timeStamp: Long): T = {
//        setState(Close, timeStamp)
//    }
//
//    def beginUnlocking(timeStamp: Long): T = {
//        setState(Closing, timeStamp)
//    }
//
//    def finishUnlocking(timeStamp: Long): T = {
//        setState(Close, timeStamp)
//    }
//
//    def beginLocking(timeStamp: Long): T = {
//        setState(Locking, timeStamp)
//    }
//
//    def finishLocking(timeStamp: Long): T = {
//        setState(Locked, timeStamp)
//    }
//
//    @Deprecated
//    def setOpenableState(openableState: OpenableState, timeStamp: Long): T = {
//        (state, openableState) match {
//            case (Open, Closing) => setState(openableState, timeStamp)
//            case (Closing, Close) => setState(openableState, timeStamp)
//            case (Close, Opening) => setState(openableState, timeStamp)
//            case (Opening, Open) => setState(openableState, timeStamp)
//
//            case (Close, Locking) => setState(openableState, timeStamp)
//            case (Locking, Locked) => setState(openableState, timeStamp)
//            case (Locked, Unlocking) => setState(openableState, timeStamp)
//            case (Unlocking, Close) => setState(openableState, timeStamp)
//
//            case _ => setState(state, this.timeStamp)
//        }
//    }
//
//
//}
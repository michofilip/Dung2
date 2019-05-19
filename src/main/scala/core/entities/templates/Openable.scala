//package core.entities.templates
//
//import core.entities.properties.StateHolder
//import core.parts.state.State._
//
//abstract class Openable[T <: Openable[T]] extends MapEntity[T] with StateHolder[T] {
//    val lockCode: Long
//
//    val openingLength = 1000
//    val closingLength = 1000
//    val unlockingLength = 1000
//    val lockingLength = 1000
//
//    def beginOpening(animationStartTime: Long): T = {
//        setState(Opening)
////                .setAnimationStartTime(animationStartTime)
//    }
//
//    def finishOpening(animationStartTime: Long): T = {
//        setState(Open)
////                .setAnimationStartTime(animationStartTime)
//    }
//
//    def beginClosing(animationStartTime: Long): T = {
//        setState(Closing)
////                .setAnimationStartTime(animationStartTime)
//    }
//
//    def finishClosing(animationStartTime: Long): T = {
//        setState(Close)
////                .setAnimationStartTime(animationStartTime)
//    }
//
//    def beginUnlocking(animationStartTime: Long): T = {
//        setState(Closing)
////                .setAnimationStartTime(animationStartTime)
//    }
//
//    def finishUnlocking(animationStartTime: Long): T = {
//        setState(Close)
////                .setAnimationStartTime(animationStartTime)
//    }
//
//    def beginLocking(animationStartTime: Long): T = {
//        setState(Locking)
////                .setAnimationStartTime(animationStartTime)
//    }
//
//    def finishLocking(animationStartTime: Long): T = {
//        setState(Locked)
////                .setAnimationStartTime(animationStartTime)
//    }
//}
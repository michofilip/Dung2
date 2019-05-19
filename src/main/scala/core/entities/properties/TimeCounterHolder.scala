//package core.entities.properties
//
//import core.entities.Entity
//import core.parts.timer.{TimeStamp, Timer}
//
//trait TimeCounterHolder[T <: TimeCounterHolder[T]] extends Entity {
//    protected val timer: Timer
//
//    def getTimeStamp: TimeStamp = {
//        timer.getTimeStamp
//    }
//
//    def isRunning: Boolean = {
//        timer.isRunning
//    }
//
//    def start(): T
//
//    def stop(): T
//}
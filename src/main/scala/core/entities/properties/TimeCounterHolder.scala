package core.entities.properties

import core.entities.Entity
import core.parts.timer.Timer

trait TimeCounterHolder[T <: TimeCounterHolder[T]] extends Entity[T] {
    protected val timer: Timer
    
    def getTime: Long = {
        timer.getTime
    }
    
    def isRunning: Boolean = {
        timer.isRunning
    }
    
    def start(): T
    
    def stop(): T
}
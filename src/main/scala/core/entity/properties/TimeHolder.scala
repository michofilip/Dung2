package core.entity.properties

import core.entity.Entity
import core.parts.timer.Timer

trait TimeHolder[T <: TimeHolder[T]] extends Entity[T] {
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
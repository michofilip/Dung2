package core.entity2.traits

import core.entity2.Entity2
import core.parts.timer.Timer

trait TimeCounterHolder2 extends Entity2 {
    protected val timer: Timer
    
    def getTime: Long = {
        timer.getTime
    }
    
    def isRunning: Boolean = {
        timer.isRunning
    }
    
    def start(): TimeCounterHolder2
    
    def stop(): TimeCounterHolder2
}
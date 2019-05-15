package core.entity2.traits

import core.entity2.Entity2
import core.parts.timer.{TimeStamp, Timer}

trait TimeCounterHolder2 extends Entity2 {
    protected val timer: Timer
    
    def getTimeStamp: TimeStamp = {
        timer.getTimeStamp
    }
    
    def isRunning: Boolean = {
        timer.isRunning
    }
    
    def start(): TimeCounterHolder2
    
    def stop(): TimeCounterHolder2
}
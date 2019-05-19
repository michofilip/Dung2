package core.entity2.traits

import core.entity2.Entity2
import core.parts.timer.{TimeStamp, Timer}

trait TimeCounterHolder2 extends Entity2 {
    protected val timer: Timer
    
    def getTimeStamp: TimeStamp = {
        timer.getTimeStamp
    }
    
    def isTimerRunning: Boolean = {
        timer.isRunning
    }
    
    def startTimer(): TimeCounterHolder2
    
    def stopTimer(): TimeCounterHolder2
}
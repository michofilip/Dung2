package core.entities.traits.properties

import core.entities.Entity
import core.parts.timer.{TimeStamp, Timer}

trait TimeCounterHolder extends Entity {
    protected val timer: Timer
    
    def getTimeStamp: TimeStamp = {
        timer.getTimeStamp
    }
    
    def isTimerRunning: Boolean = {
        timer.isRunning
    }
    
    def startTimer(): TimeCounterHolder
    
    def stopTimer(): TimeCounterHolder
}

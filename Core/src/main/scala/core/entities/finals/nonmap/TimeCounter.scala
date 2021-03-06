package core.entities.finals.nonmap

import core.entities.traits.properties.TimeCounterProperty
import core.parts.timer.Timer

final class TimeCounter(override val id: Long,
                        override val timer: Timer
                        ) extends TimeCounterProperty {
    private def update(timer: Timer = timer): TimeCounter = {
        new TimeCounter(id, timer)
    }
    
    override def startTimer(): TimeCounter = {
        update(timer.start)
    }
    
    override def stopTimer(): TimeCounter = {
        update(timer.stop)
    }
    
    override def isTimerRunning: Boolean = {
        timer.isRunning
    }
}
package core.entity2.finals.nonmap

import core.entity2.traits.properties.TimeCounterHolder2
import core.parts.timer.Timer

final class TimeCounter2(override val id: Long,
                         override val timer: Timer
                        ) extends TimeCounterHolder2 {
    private def update(timer: Timer = timer): TimeCounter2 = {
        new TimeCounter2(id, timer)
    }
    
    override def startTimer(): TimeCounter2 = {
        update(timer.start)
    }
    
    override def stopTimer(): TimeCounter2 = {
        update(timer.stop)
    }
    
    override def isTimerRunning: Boolean = {
        timer.isRunning
    }
}
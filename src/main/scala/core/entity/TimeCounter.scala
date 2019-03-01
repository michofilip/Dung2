package core.entity

import core.entity.properties.TimeHolder
import core.parts.timer.Timer
import json.{JValue, MyJ}

final class TimeCounter(_id: String, _timer: Timer) extends Entity with TimeHolder {
    override protected type T = TimeCounter
    override val id: String = _id
    val timer: Timer = _timer
    
    private def update(timer: Timer = timer): T =
        new TimeCounter(id, timer)
    
    override def start(): TimeCounter = {
        update(timer.start)
    }
    
    override def stop(): TimeCounter = {
        update(timer.stop)
    }
    
    override def isRunning: Boolean = {
        timer.isRunning
    }
    
    override def toJSON: JValue = {
        MyJ.jObject(
            "entity" -> "TimeCounter",
            "id" -> id,
            "time" -> timer.getTime
        )
    }
}
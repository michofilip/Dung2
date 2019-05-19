//package core.entities.finals
//
//import core.entities.properties.TimeCounterHolder
//import core.parts.timer.Timer
//import json.{JValue, MyJ}
//
//final class TimeCounter(override val id: String,
//                        override val timer: Timer
//                       ) extends TimeCounterHolder[TimeCounter] {
//    private def update(timer: Timer = timer): TimeCounter = {
//        new TimeCounter(id, timer)
//    }
//
//    override def start(): TimeCounter = {
//        update(timer.start)
//    }
//
//    override def stop(): TimeCounter = {
//        update(timer.stop)
//    }
//
//    override def isRunning: Boolean = {
//        timer.isRunning
//    }
//
//    override def toJSON: JValue = {
//        MyJ.jObject(
//            "class" -> "TimeCounter",
//            "id" -> id,
//            "time" -> timer.getTimeStamp
//        )
//    }
//}
package core.parts.timer

case class TimeStamp(milliseconds: Long) {
    def shift(duration: Duration): TimeStamp =
        TimeStamp(milliseconds + duration.milliseconds)
    
    def shiftBack(duration: Duration): TimeStamp =
        TimeStamp(milliseconds - duration.milliseconds)
}

object TimeStamp {
    def zero: TimeStamp =
        TimeStamp(0)
    
    def now(): TimeStamp =
        TimeStamp(System.currentTimeMillis())
}

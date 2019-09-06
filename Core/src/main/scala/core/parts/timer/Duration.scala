package core.parts.timer

case class Duration(milliseconds: Long)

object Duration {
    def between(fromTimeStamp: TimeStamp, toTimeStamp: TimeStamp): Duration =
        Duration(toTimeStamp.milliseconds - fromTimeStamp.milliseconds)
}
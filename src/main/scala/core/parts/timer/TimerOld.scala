//package core.parts.timer
//
//class TimerOld(private val initialTime: Long, val isRunning: Boolean) {
//    private val creationTime: Long = System.currentTimeMillis()
//
//    def start: TimerOld = {
//        new TimerOld(initialTime, true)
//    }
//
//    def stop: TimerOld = {
//        new TimerOld(getTime, false)
//    }
//
//    def getTime: Long = {
//        if (isRunning) {
//            initialTime + System.currentTimeMillis() - creationTime
//        } else {
//            initialTime
//        }
//    }
//}

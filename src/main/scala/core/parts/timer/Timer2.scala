package core.parts.timer

class Timer2(private val initialTimeStamp: TimeStamp, val isRunning: Boolean) {
    private val creationTimeStamp: TimeStamp = TimeStamp.now()
    
    def start: Timer2 = {
        new Timer2(initialTimeStamp, true)
    }
    
    def stop: Timer2 = {
        new Timer2(getTimeStamp, false)
    }
    
    def getTimeStamp: TimeStamp = {
        if (isRunning) {
            initialTimeStamp.shift(Duration.between(creationTimeStamp, TimeStamp.now()))
        } else {
            initialTimeStamp
        }
    }
}

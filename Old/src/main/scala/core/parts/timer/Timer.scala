package core.parts.timer

class Timer(private val initialTimeStamp: TimeStamp = TimeStamp(0), val isRunning: Boolean = false) {
    
    private val creationTimeStamp: TimeStamp = TimeStamp.now()
    
    def start: Timer = {
        new Timer(initialTimeStamp, true)
    }
    
    def stop: Timer = {
        new Timer(getTimeStamp, false)
    }
    
    def getTimeStamp: TimeStamp = {
        if (isRunning) {
            initialTimeStamp.shift(Duration.between(creationTimeStamp, TimeStamp.now()))
        } else {
            initialTimeStamp
        }
    }
}

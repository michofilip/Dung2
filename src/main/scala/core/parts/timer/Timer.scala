package core.parts.timer

class Timer(private val initialTime: Long, val isRunning: Boolean) {
    private val creationTime: Long = System.currentTimeMillis()
    
    def start: Timer = {
        new Timer(initialTime, true)
    }
    
    def stop: Timer = {
        new Timer(getTime, false)
    }
    
    def getTime: Long = {
        if (isRunning) {
            initialTime + System.currentTimeMillis() - creationTime
        } else {
            initialTime
        }
    }
}

package core.parts.graphics

import core.parts.timer.TimeStamp

class Animation(private val frames: Vector[Frame], private val length: Int, private val looped: Boolean) {
    def getFrame(timeStamp: TimeStamp): Frame = {
        val milliseconds = timeStamp.milliseconds
        val frameNo: Int = Math.max(frames.length * milliseconds / length, 0).toInt
        
        if (looped) {
            frames(frameNo % frames.length)
        } else {
            frames(Math.min(frameNo, frames.length - 1))
        }
    }
    
    def reverse: Animation = {
        new Animation(frames.reverse, length, looped)
    }
}
package core.parts.graphics

import core.parts.timer.TimeStamp
import math.MyMath.{Mod, restrict}

class Animation(private val frames: Vector[Frame], private val duration: Long, private val looped: Boolean) {
    
    def getFrame(timeStamp: TimeStamp): Frame = {
        val milliseconds = timeStamp.milliseconds
        val frameNo: Int = (frames.length * milliseconds / duration).toInt
        
        if (looped) {
            frames(frameNo %% frames.length)
        } else {
            frames(restrict(frameNo, 0, frames.length))
        }
    }
    
    def reverse: Animation = {
        new Animation(frames.reverse, duration, looped)
    }
}
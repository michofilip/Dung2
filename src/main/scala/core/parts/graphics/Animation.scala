package core.parts.graphics

import core.parts.timer.TimeStamp
import math.MyMath.{Mod, restrict}

case class Animation(frames: Vector[Frame], duration: Long, initialOffset: Long, looped: Boolean) {
    
    private def update(frames: Vector[Frame] = frames,
                       duration: Long = duration,
                       initialOffset: Long = initialOffset,
                       lopped: Boolean = looped): Animation = {
        Animation(frames, duration, initialOffset, looped)
    }
    
    def getFrame(timeStamp: TimeStamp): Frame = {
        val milliseconds = timeStamp.milliseconds + initialOffset
        val frameNo: Int = (frames.length * milliseconds / duration).toInt
        
        if (looped) {
            frames(frameNo %% frames.length)
        } else {
            frames(restrict(frameNo, 0, frames.length))
        }
    }
    
    def reverse: Animation = {
        update(frames = frames.reverse)
    }
    
    def setInitialOffset(initialOffset: Long): Animation = {
        update(initialOffset = initialOffset)
    }
    
    def setLooped(lopped: Boolean): Animation = {
        update(lopped = lopped)
    }
    
}
package core.parts.graphics

import math.MyMath.{Mod, restrict}

case class Animation(frames: Vector[Frame], duration: Long, looped: Boolean) {
    
    private def update(frames: Vector[Frame] = frames, duration: Long = duration, lopped: Boolean = looped): Animation = {
        Animation(frames, duration, looped)
    }
    
    def getFrame(milliseconds: Long): Frame = {
        val frameNo: Int = (frames.length * milliseconds / duration).toInt
        
        if (looped) {
            frames(frameNo %% frames.length)
        } else {
            frames(restrict(frameNo, 0, frames.length))
        }
    }
    
}
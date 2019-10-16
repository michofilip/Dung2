package entity.parts

import math.CustomMath._


case class Animation(frames: Vector[Frame], duration: Long, isLooped: Boolean) {
    
    def getFrame(milliseconds: Long): Frame = {
        val frameNo: Int = (frames.length * milliseconds / duration).toInt
        
        if (isLooped) frames(frameNo %% frames.length)
        else frames(frameNo <| (0, frames.length - 1))
    }
    
}
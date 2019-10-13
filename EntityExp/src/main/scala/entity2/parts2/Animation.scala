package entity2.parts2

case class Animation(frames: Vector[Frame], duration: Long, isLooped: Boolean) {
    
    //    def getFrame(milliseconds: Long): Frame = {
    //        val frameNo: Int = (frames.length * milliseconds / duration).toInt
    //
    //        if (looped) frames(frameNo %% frames.length)
    //        else frames(bound(frameNo, 0, frames.length))
    //    }
    
}
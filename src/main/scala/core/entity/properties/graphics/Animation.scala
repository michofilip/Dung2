package core.entity.properties.graphics

class Animation(private val frames: Vector[Frame], private val length: Int, private val looped: Boolean) {
    def getFrame(time: Long): Frame = {
        val frameNo: Int = Math.max(frames.length * time / length, 0).toInt
        
        if (looped)
            frames(frameNo % frames.length)
        else
            frames(Math.min(frameNo, frames.length - 1))
    }
    
    def getFrame1(fraction: Double): Frame = {
        
        ???
    }
    
    def reverse: Animation = new Animation(frames.reverse, length, looped)
}
package parts

import math.CustomMath._

sealed abstract class Animation {
    def getFrame(milliseconds: Long): Frame
}

object Animation {
    
    final case class SingleFrameAnimation(frame: Frame) extends Animation {
        override def getFrame(milliseconds: Long): Frame = frame
    }
    
    final case class OneWayAnimation(frames: Vector[Frame], fps: Double) extends Animation {
        private val frameNo = (milliseconds: Long) => (milliseconds * fps).toInt.bound(0, frames.length - 1)
        
        override def getFrame(milliseconds: Long): Frame = frames(frameNo(milliseconds))
    }
    
    final case class LoopedAnimation(frames: Vector[Frame], fps: Double) extends Animation {
        private val frameNo = (milliseconds: Long) => (milliseconds * fps).toInt.mod(frames.length)
        
        override def getFrame(milliseconds: Long): Frame = frames(frameNo(milliseconds))
    }
    
}
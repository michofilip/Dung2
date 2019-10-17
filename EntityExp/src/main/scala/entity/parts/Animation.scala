package entity.parts

import math.CustomMath._

sealed abstract class Animation {
    def getFrame(milliseconds: Long): Frame
}

object Animation {
    
    final case class SingleFrameAnimation(frame: Frame) extends Animation {
        override def getFrame(milliseconds: Long): Frame = frame
    }
    
    final case class OneWayAnimation(frames: Vector[Frame], duration: Long) extends Animation {
        private val frameNo = (milliseconds: Long) => (frames.length * milliseconds / duration).toInt
        
        override def getFrame(milliseconds: Long): Frame = frames(frameNo(milliseconds).bound(0, frames.length - 1))
    }
    
    final case class LoopedAnimation(frames: Vector[Frame], duration: Long) extends Animation {
        private val frameNo = (milliseconds: Long) => (frames.length * milliseconds / duration).toInt
        
        override def getFrame(milliseconds: Long): Frame = frames(frameNo(milliseconds).mod(frames.length))
    }
    
}
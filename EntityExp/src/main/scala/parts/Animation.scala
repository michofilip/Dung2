package parts

import math.CustomMath._

sealed abstract class Animation {
    def getFrame(time: Long): Frame
}

object Animation {
    
    final case class SingleFrameAnimation(frame: Frame) extends Animation {
        override def getFrame(time: Long): Frame = frame
    }
    
    final case class OneWayAnimation(frames: Vector[Frame], fps: Double) extends Animation {
        override def getFrame(time: Long): Frame = frames((time * fps).toInt.bound(0, frames.length - 1))
    }
    
    final case class LoopedAnimation(frames: Vector[Frame], fps: Double) extends Animation {
        override def getFrame(time: Long): Frame = frames((time * fps).toInt.mod(frames.length))
    }
    
}
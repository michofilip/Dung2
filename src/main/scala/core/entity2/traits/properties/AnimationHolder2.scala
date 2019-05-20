package core.entity2.traits.properties

import core.entity2.Entity2
import core.parts.graphics.{Animation, AnimationSelector2, Frame}
import core.parts.timer.{Duration, TimeStamp}

trait AnimationHolder2 extends Entity2 {
    protected val animationSelector: AnimationSelector2
    protected val animationBeginningTimeStamp: TimeStamp
    
    protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): AnimationHolder2
    
    def animationSelectorId: String = {
        animationSelector.id
    }
    
    private def animation: Animation = {
        val stateOpt = this match {
            case en: StateHolder2 => Some(en.state)
            case _ => None
        }
        val directionOpt = this match {
            case en: PositionHolder2 => Some(en.position.direction)
            case _ => None
        }
        animationSelector.getAnimation(stateOpt, directionOpt)
    }
    
    def getFrame(timeStamp: TimeStamp): Frame = {
        animation.getFrame(Duration.between(animationBeginningTimeStamp, timeStamp).milliseconds)
    }
}

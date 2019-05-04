package core.entities.properties

import core.entities.Entity
import core.entities.selectors.AnimationSelector
import core.parts.graphics.{Animation, Frame}
import core.parts.timer.TimeStamp

trait AnimationHolder[T <: AnimationHolder[T]] extends Entity {
    protected val animationSelector: AnimationSelector
    //    protected val animationStartTime: Long
    
//    protected def setAnimationStartTime(animationStartTime: Long): T
    
    def animationSelectorId: String = {
        animationSelector.id
    }
    
    private def animation: Animation = {
        val stateOpt = this match {
            case en: StateHolder[_] => Some(en.state)
            case _ => None
        }
        val directionOpt = this match {
            case en: PositionHolder[_] => Some(en.position.direction)
            case _ => None
        }
        animationSelector.getAnimation(stateOpt, directionOpt)
    }
    
    def getFrame(timeStamp: TimeStamp): Frame = {
        animation.getFrame(timeStamp)
    }
}
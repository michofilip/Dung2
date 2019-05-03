package core.entity2.traits

import core.entities.selectors.AnimationSelector
import core.entity2.Entity2
import core.parts.graphics.{Animation, Frame}
import core.parts.timer.Timer

trait AnimationHolder2 extends Entity2 {
    protected val animationSelector: AnimationSelector
    protected val animationStartTime: Long
    
    protected def setAnimationStartTime(animationStartTime: Long): AnimationHolder2
    
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
    
    def getFrame(implicit timer: Timer): Frame = {
        animation.getFrame(timer.getTime - animationStartTime)
    }
}
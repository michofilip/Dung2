package core.entity.properties

import core.entity.Entity
import core.parts.graphics.{Animation, Frame}
import core.entity.selectors.AnimationSelector
import core.parts.timer.Timer

trait AnimationHolder extends Entity {
    protected val animationSelector: AnimationSelector
    protected val animationStartTime: Long
    
    protected def setAnimationStartTime(animationStartTime: Long): T
    
    def animationSelectorId: String = {
        animationSelector.id
    }
    
    private def animation: Animation = {
        val stateOpt = this match {
            case en: StateHolder => Some(en.state)
            case _ => None
        }
        val directionOpt = this match {
            case en: PositionHolder => Some(en.position.direction)
            case _ => None
        }
        animationSelector.getAnimation(stateOpt, directionOpt)
    }
    
    def getFrame(implicit timer: Timer): Frame = {
        animation.getFrame(timer.getTime - animationStartTime)
    }
}
package core.entities.traits.properties

import core.entities.Entity
import core.parts.graphics.{Animation, AnimationSelector, Frame}
import core.parts.timer.{Duration, TimeStamp}

trait AnimationProperty extends Entity {
    protected val animationSelector: AnimationSelector
    protected val animationBeginningTimeStamp: TimeStamp
    
    protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): AnimationProperty
    
    def animationSelectorId: String = animationSelector.id
    
    private def animation: Animation = {
        val stateOpt = this match {
            case en: StateProperty => Some(en.state)
            case _ => None
        }
        val directionOpt = this match {
            case en: PositionProperty => Some(en.position.direction)
            case _ => None
        }
        animationSelector.getAnimation(stateOpt, directionOpt)
    }
    
    def getFrame(timeStamp: TimeStamp): Frame =
        animation.getFrame(Duration.between(animationBeginningTimeStamp, timeStamp).milliseconds)
}

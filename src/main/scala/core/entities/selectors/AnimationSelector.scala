package core.entities.selectors

import core.parts.graphics.{Animation, Frame}
import core.parts.position.Direction
import core.parts.state.State
import core.parts.state.State._

sealed abstract class AnimationSelector {
    val id: String
    
    def getAnimation(stateOpt: Option[State], directionOpt: Option[Direction]): Animation
}

object AnimationSelector {
    
    case object LeverAnimationSelector extends AnimationSelector {
        override val id: String = "LeverAnimationSelector"
        
        override def getAnimation(stateOpt: Option[State], directionOpt: Option[Direction]): Animation = {
            val offAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            val switchingOffAnimation = new Animation(frames = Vector(
                Frame(frameId = 104),
                Frame(frameId = 105),
                Frame(frameId = 106),
                Frame(frameId = 107)
            ), duration = 1000, 0, looped = false)
            val switchingOnAnimation = new Animation(frames = Vector(
                Frame(frameId = 107),
                Frame(frameId = 106),
                Frame(frameId = 105),
                Frame(frameId = 104)
            ), duration = 1000, 0, looped = false)
            val onAnimation = new Animation(frames = Vector(
                Frame(frameId = 108),
                Frame(frameId = 109),
                Frame(frameId = 110),
                Frame(frameId = 111)
            ), duration = 1000, 0, looped = true)
            
            (stateOpt, directionOpt) match {
                case (Some(Off), _) => offAnimation
                case (Some(SwitchingOff), _) => switchingOffAnimation
                case (Some(SwitchingOn), _) => switchingOnAnimation
                case (Some(On), _) => onAnimation
                case _ => offAnimation
            }
        }
    }
    
    case object DoorAnimationSelector extends AnimationSelector {
        override val id: String = "DoorAnimationSelector"
        
        override def getAnimation(stateOpt: Option[State], directionOpt: Option[Direction]): Animation = {
            val openAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            val openingOffAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            val closingAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            val closeAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            val unlockingAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            val lockingAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            val lockedAnimation = new Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, 0, looped = true)
            
            (stateOpt, directionOpt) match {
                case (Some(Open), _) => openAnimation
                case (Some(Opening), _) => openingOffAnimation
                case (Some(Closing), _) => closingAnimation
                case (Some(Close), _) => closeAnimation
                case (Some(Unlocking), _) => unlockingAnimation
                case (Some(Locking), _) => lockingAnimation
                case (Some(Locked), _) => lockedAnimation
                case _ => openAnimation
            }
        }
    }
    
}

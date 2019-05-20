package core.parts.graphics

import core.parts.position.Direction
import core.parts.state.State
import core.parts.state.State._

sealed abstract class AnimationSelector2 {
    val id: String
    
    def getAnimation(stateOpt: Option[State], directionOpt: Option[Direction]): Animation
}


object AnimationSelector2 {
    
    case object LeverAnimationSelector extends AnimationSelector2 {
        override val id: String = "LeverAnimationSelector"
        
        override def getAnimation(stateOpt: Option[State], directionOpt: Option[Direction]): Animation = {
            val offAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            val switchingOffAnimation = Animation(frames = Vector(
                Frame(frameId = 104),
                Frame(frameId = 105),
                Frame(frameId = 106),
                Frame(frameId = 107)
            ), duration = 1000, initialOffset = 0, looped = false)
            val switchingOnAnimation = Animation(frames = Vector(
                Frame(frameId = 107),
                Frame(frameId = 106),
                Frame(frameId = 105),
                Frame(frameId = 104)
            ), duration = 1000, initialOffset = 0, looped = false)
            val onAnimation = Animation(frames = Vector(
                Frame(frameId = 108),
                Frame(frameId = 109),
                Frame(frameId = 110),
                Frame(frameId = 111)
            ), duration = 1000, initialOffset = 0, looped = true)
            
            (stateOpt, directionOpt) match {
                case (Some(Off), _) => offAnimation
                case (Some(SwitchingOff), _) => switchingOffAnimation
                case (Some(SwitchingOn), _) => switchingOnAnimation
                case (Some(On), _) => onAnimation
                case _ => offAnimation
            }
        }
    }
    
    case object DoorAnimationSelector extends AnimationSelector2 {
        override val id: String = "DoorAnimationSelector"
        
        override def getAnimation(stateOpt: Option[State], directionOpt: Option[Direction]): Animation = {
            val openAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            val openingOffAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            val closingAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            val closeAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            val unlockingAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            val lockingAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            val lockedAnimation = Animation(frames = Vector(
                Frame(frameId = 100),
                Frame(frameId = 101),
                Frame(frameId = 102),
                Frame(frameId = 103)
            ), duration = 1000, initialOffset = 0, looped = true)
            
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

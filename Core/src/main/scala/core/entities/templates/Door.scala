package core.entities.templates

import core.entities.Entity
import core.entities.traits.properties.{AnimationProperty, PhysicsProperty, PositionProperty, StateProperty}
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.state.State.{Close, Closing, Locked, Locking, Open, Opening, Unlocking}
import core.parts.timer.TimeStamp

class Door(override val id: Long,
           override val position: Position,
           override val state: State,
           val lockCode: Long,
           override protected val animationBeginningTimeStamp: TimeStamp,
           override protected val physicsSelector: PhysicsSelector,
           override protected val animationSelector: AnimationSelector)
        extends Entity with PositionProperty with PhysicsProperty with AnimationProperty with StateProperty {
    
    val openingDuration: Long =
        animationSelector.getAnimation(Some(Opening), Some(position.direction)).duration
    val closingDuration: Long =
        animationSelector.getAnimation(Some(Closing), Some(position.direction)).duration
    val unlockingDuration: Long =
        animationSelector.getAnimation(Some(Unlocking), Some(position.direction)).duration
    val lockingDuration: Long =
        animationSelector.getAnimation(Some(Locking), Some(position.direction)).duration
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Door =
        new Door(id, position, state, lockCode, animationBeginningTimeStamp, physicsSelector, animationSelector)
    
    override protected def setPosition(position: Position): Door =
        update(position = position)
    
    override protected def setState(state: State): Door =
        update(state = state)
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Door =
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
    
    def beginOpening(timeStamp: TimeStamp): Door =
        if (state == Close) setState(Opening).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishOpening(timeStamp: TimeStamp): Door =
        if (state == Opening) setState(Open).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginClosing(timeStamp: TimeStamp): Door =
        if (state == Open) setState(Closing).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishClosing(timeStamp: TimeStamp): Door =
        if (state == Closing) setState(Close).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginUnlocking(timeStamp: TimeStamp): Door =
        if (state == Locked) setState(Unlocking).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishUnlocking(timeStamp: TimeStamp): Door =
        if (state == Unlocking) setState(Close).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginLocking(timeStamp: TimeStamp): Door =
        if (state == Close) setState(Locking).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishLocking(timeStamp: TimeStamp): Door =
        if (state == Locking) setState(Locked).setAnimationBeginningTimeStamp(timeStamp)
        else this
}

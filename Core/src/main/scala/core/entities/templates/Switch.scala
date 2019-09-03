package core.entities.templates

import core.entities.Entity
import core.entities.traits.properties.{AnimationProperty, PhysicsProperty, PositionProperty, StateProperty}
import core.parts.graphics.AnimationSelector
import core.parts.physics.PhysicsSelector
import core.parts.position.Position
import core.parts.state.State
import core.parts.state.State.{Off, On, SwitchingOff, SwitchingOn}
import core.parts.timer.TimeStamp

class Switch(override val id: Long,
             override val position: Position,
             override val state: State,
             override protected val animationBeginningTimeStamp: TimeStamp,
             override protected val physicsSelector: PhysicsSelector,
             override protected val animationSelector: AnimationSelector)
        extends Entity with PositionProperty with PhysicsProperty with AnimationProperty with StateProperty {
    
    val switchingOffDuration: Long =
        animationSelector.getAnimation(Some(SwitchingOff), Some(position.direction)).duration
    val switchingOnDuration: Long =
        animationSelector.getAnimation(Some(SwitchingOn), Some(position.direction)).duration
    
    private def update(position: Position = position, state: State = state,
                       animationBeginningTimeStamp: TimeStamp = animationBeginningTimeStamp): Switch =
        new Switch(id, position, state, animationBeginningTimeStamp, physicsSelector, animationSelector)
    
    override protected def setPosition(position: Position): Switch =
        update(position = position)
    
    override protected def setState(state: State): Switch =
        update(state = state)
    
    override protected def setAnimationBeginningTimeStamp(animationBeginningTimeStamp: TimeStamp): Switch =
        update(animationBeginningTimeStamp = animationBeginningTimeStamp)
    
    def beginSwitchingOff(timeStamp: TimeStamp): Switch =
        if (state == On) setState(SwitchingOff).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishSwitchingOff(timeStamp: TimeStamp): Switch =
        if (state == SwitchingOff) setState(Off).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def beginSwitchingOn(timeStamp: TimeStamp): Switch =
        if (state == Off) setState(SwitchingOn).setAnimationBeginningTimeStamp(timeStamp)
        else this
    
    def finishSwitchingOn(timeStamp: TimeStamp): Switch =
        if (state == SwitchingOn) setState(On).setAnimationBeginningTimeStamp(timeStamp)
        else this
}

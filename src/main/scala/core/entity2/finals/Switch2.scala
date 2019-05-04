package core.entity2.finals

import core.entities.selectors.{AnimationSelector, PhysicsSelector}
import core.entity2.Entity2
import core.entity2.traits._
import core.parts.position.{Direction, Position}
import core.parts.state.State

import scala.language.implicitConversions

final class Switch2(override val id: Long,
                    override val position: Position,
                    override protected val physicsSelector: PhysicsSelector,
                    override protected val animationSelector: AnimationSelector,
//                    override protected val animationStartTime: Long,
                    override val state: State,
                    override val switchingOffDuration: Int,
                    override val switchingOnDuration: Int
                   ) extends PositionHolder2 with PhysicsHolder2 with AnimationHolder2 with SwitchingCapable {
    
    private def update(position: Position = position/*, animationStartTime: Long = animationStartTime*/, state: State = state): Switch2 = {
        new Switch2(id, position, physicsSelector, animationSelector/*, animationStartTime*/, state, switchingOffDuration, switchingOnDuration)
    }
    
    override protected def setPosition(position: Position): Switch2 = {
        update(position = position)
    }
    
//    override protected def setAnimationStartTime(animationStartTime: Long): Switch2 = {
//        update(animationStartTime = animationStartTime)
//    }
    
    override protected def setState(state: State): Switch2 = {
        update(state = state)
    }
    
//    override def enableMovement(): Switch2 = super.enableMovement()
//
//    override def disableMovement(): Switch2 = super.disableMovement()
//
//    override def enableRotation(): Switch2 = super.enableRotation()
//
//    override def disableRotation(): Switch2 = super.disableRotation()
//
//    override def moveTo(x: Int, y: Int): Switch2 = super.moveTo(x, y)
//
//    override def moveBy(dx: Int, dy: Int): Switch2 = super.moveBy(dx, dy)
//
//    override def rotateTo(direction: Direction): Switch2 = super.rotateTo(direction)
//
//    override def rotate45Clockwise(): Switch2 = super.rotate45Clockwise()
//
//    override def rotate90Clockwise(): Switch2 = super.rotate90Clockwise()
//
//    override def rotate45Counterclockwise(): Switch2 = super.rotate45Counterclockwise()
//
//    override def rotate90Counterclockwise(): Switch2 = super.rotate90Counterclockwise()
//
//    override def rotate180(): Switch2 = super.rotate180()
//
//    override def beginSwitchingOff(): Switch2 = super.beginSwitchingOff()
//
//    override def finishSwitchingOff(): Switch2 = super.finishSwitchingOff()
//
//    override def beginSwitchingOn(): Switch2 = super.beginSwitchingOn()
//
//    override def finishSwitchingOn(): Switch2 = super.finishSwitchingOn()
}

object Switch2 {
    implicit private def toStatic(entity: Entity2): Switch2 = entity.asInstanceOf[Switch2]
}
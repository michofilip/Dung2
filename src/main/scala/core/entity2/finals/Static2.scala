package core.entity2.finals

import core.entities.selectors.{AnimationSelector, PhysicsSelector}
import core.entity2.Entity2
import core.entity2.traits.{AnimationHolder2, PhysicsHolder2, PositionHolder2}
import core.parts.position.{Direction, Position}

import scala.language.implicitConversions

class Static2(override val id: Long,
              override val position: Position,
              override protected val physicsSelector: PhysicsSelector,
              override protected val animationSelector: AnimationSelector,
              override protected val animationStartTime: Long
             ) extends PositionHolder2 with PhysicsHolder2 with AnimationHolder2 {
    
    private def update(position: Position = position, animationStartTime: Long = animationStartTime): Static2 = {
        new Static2(id, position, physicsSelector, animationSelector, animationStartTime)
    }
    
    override protected def setPosition(position: Position): Static2 = {
        update(position = position)
    }
    
    override protected def setAnimationStartTime(animationStartTime: Long): Static2 = {
        update(animationStartTime = animationStartTime)
    }
    
    override def enableMovement(): Static2 = super.enableMovement()
    
    override def disableMovement(): Static2 = super.disableMovement()
    
    override def enableRotation(): Static2 = super.enableRotation()
    
    override def disableRotation(): Static2 = super.disableRotation()
    
    override def moveTo(x: Int, y: Int): Static2 = super.moveTo(x, y)
    
    override def moveBy(dx: Int, dy: Int): Static2 = super.moveBy(dx, dy)
    
    override def rotateTo(direction: Direction): Static2 = super.rotateTo(direction)
    
    override def rotate45Clockwise(): Static2 = super.rotate45Clockwise()
    
    override def rotate90Clockwise(): Static2 = super.rotate90Clockwise()
    
    override def rotate45Counterclockwise(): Static2 = super.rotate45Counterclockwise()
    
    override def rotate90Counterclockwise(): Static2 = super.rotate90Counterclockwise()
    
    override def rotate180(): Static2 = super.rotate180()
    
}

object Static2 {
    implicit private def toStatic(entity: Entity2): Static2 = entity.asInstanceOf[Static2]
}

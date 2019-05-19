//package core.entities.finals
//
//import core.entities.selectors.{AnimationSelector, PhysicsSelector2}
//import core.entities.templates.MapEntity
//import core.parts.position.Position
//import json.{JValue, MyJ}
//
//final class Static(override val id: String,
//                   override val position: Position,
//                   override protected val physicsSelector: PhysicsSelector2,
//                   override protected val animationSelector: AnimationSelector,
////                   override protected val animationStartTime: Long
//                  ) extends MapEntity[Static] {
//    private def update(position: Position = position/*, animationStartTime: Long = animationStartTime*/): Static = {
//        new Static(id, position, physicsSelector, animationSelector/*, animationStartTime*/)
//    }
//
//    override protected def setPosition(position: Position): Static = {
//        update(position = position)
//    }
//
////    override protected def setAnimationStartTime(animationStartTime: Long): Static = {
////        update(animationStartTime = animationStartTime)
////    }
//
//    override def toJSON: JValue = {
//        MyJ.jObject(
//            "class" -> "Static",
//            "id" -> id,
//            "position" -> position,
//            "physicsSelector" -> physicsSelector.id,
//            "animationSelector" -> animationSelector.id,
////            "animationStartTime" -> animationStartTime
//        )
//    }
//}
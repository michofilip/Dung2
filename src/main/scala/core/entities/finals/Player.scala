//package core.entities.finals
//
//import core.entities.selectors.{AnimationSelector, PhysicsSelector2}
//import core.entities.templates.Character
//import core.parts.position.Position
//import core.parts.state.State
//import json.{JValue, MyJ}
//
//final class Player(override val id: String,
//                   override val position: Position,
//                   override protected val physicsSelector: PhysicsSelector2,
//                   override protected val animationSelector: AnimationSelector,
////                   override protected val animationStartTime: Long,
//                   override val state: State)
//        extends Character[Player] {
//
//    private def update(position: Position = position, /*animationStartTime: Long = animationStartTime,*/ state: State = state): Player = {
//        new Player(id, position, physicsSelector, animationSelector, /*animationStartTime,*/ state)
//    }
//
//    override protected def setPosition(position: Position): Player = {
//        update(position = position)
//    }
//
////    override protected def setAnimationStartTime(animationStartTime: Long): Player = {
////        update(animationStartTime = animationStartTime)
////    }
//
//    override protected def setState(state: State): Player = {
//        update(state = state)
//    }
//
//    override def toJSON: JValue = {
//        MyJ.jObject(
//            "class" -> "Player",
//            "id" -> id,
//            "position" -> position,
//            "physicsSelector" -> physicsSelector.id,
//            "animationSelector" -> animationSelector.id,
////            "animationStartTime" -> animationStartTime,
//            "state" -> state.toString
//        )
//    }
//}
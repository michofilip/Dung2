//package core.entities.finals
//
//import core.entities.properties.TurnCounterHolder
//import json.{JValue, MyJ}
//
//final class TurnCounter(override val id: String,
//                        override val turn: Long
//                       ) extends TurnCounterHolder[TurnCounter] {
//    private def update(turn: Long = turn): TurnCounter = {
//        new TurnCounter(id, turn)
//    }
//
//    override def nextTurn: TurnCounter = {
//        update(turn = turn)
//    }
//
//    override def toJSON: JValue = {
//        MyJ.jObject(
//            "class" -> "TurnCounter",
//            "id" -> id,
//            "turn" -> turn
//        )
//    }
//}
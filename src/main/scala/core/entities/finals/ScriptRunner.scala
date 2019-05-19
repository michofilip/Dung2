//package core.entities.finals
//
//import core.entities.properties.ScriptRunnerHolder
//import json.{JValue, MyJ}
//
//final class ScriptRunner(override val id: String) extends ScriptRunnerHolder[ScriptRunner] {
//    override def toJSON: JValue = {
//        MyJ.jObject(
//            "class" -> "ScriptRunner",
//            "id" -> id
//        )
//    }
//}
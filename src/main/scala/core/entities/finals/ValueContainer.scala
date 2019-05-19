//package core.entities.finals
//
//import core.entities.properties.ValueHolder
//import core.parts.value.Value
//import json.{JValue, MyJ}
//
//final class ValueContainer(override val id: String,
//                           override protected val values: Map[String, Value]
//                          ) extends ValueHolder[ValueContainer] {
//
//    private def update(values: Map[String, Value] = values): ValueContainer = {
//        new ValueContainer(id, values)
//    }
//
//    override def setValue(name: String, value: Value): ValueContainer = {
//        update(values = values + (name -> value))
//    }
//
//    override def removeValue(name: String): ValueContainer = {
//        update(values = values - name)
//    }
//
//    override def toJSON: JValue = {
//        MyJ.jObject(
//            "class" -> "ValueContainer",
//            "id" -> id,
//            "values" -> values
//        )
//    }
//}
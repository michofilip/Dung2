package core.value.basic

import core.entity.EntityHolder
import core.value.Value
import json.JValue

case object UnitValue extends Value {
    override type T = Unit
    
    override def get(implicit entityHolder: EntityHolder): Option[Unit] = {
        None
    }
    
    override def toJSON: JValue = {
        import json.MyJ._
        jObject(
            "class" -> this.getClass.getSimpleName
        )
    }
}
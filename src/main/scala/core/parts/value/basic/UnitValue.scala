package core.parts.value.basic

import core.entity.repositoy.EntityRepository
import core.parts.value.Value
import json.JValue

// todo cam up with a better implementation
object UnitValue extends Value {
    override type T = this.type
    
    override def get(implicit entityHolder: EntityRepository): Option[UnitValue.type] = {
        None
    }
    
    override def toJSON: JValue = {
        import json.MyJ._
        jObject(
            "class" -> this.getClass.getSimpleName
        )
    }
}

package core.parts.value.basic

import core.entities.repositoy.EntityRepository
import core.parts.value.Value
import json.JValue

// todo come up with a better implementation
case object NullValue extends Value {
    override type T = Unit
    
    override def get(implicit entityHolder: EntityRepository): Option[Unit] = {
        None
    }
    
    override def toJSON: JValue = {
        import json.MyJ._
        jObject(
            "class" -> this.getClass.getSimpleName
        )
    }
}

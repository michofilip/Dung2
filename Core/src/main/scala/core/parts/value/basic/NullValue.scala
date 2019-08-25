package core.parts.value.basic

import core.repository.EntityRepository
import core.parts.value.Value
import json.JValue

// todo come up with a better implementation
case object NullValue extends Value {
    override type T = Unit
    
    override def get(implicit entityRepository: EntityRepository): Option[Unit] = {
        None
    }
    
    override def toJSON: JValue = {
        import json.MyJ._
        jObject(
            "class" -> this.getClass.getSimpleName
        )
    }
}

package core.parts.value.custom

import core.entities.properties.ValueHolder
import core.entities.repositoy.EntityRepository
import core.parts.value.basic.DoubleValue
import json.JValue

object CustomDoubleValue {
    
    final case class GetDoubleValue(entityId: String, name: String) extends DoubleValue {
        override def get(implicit entityHolder: EntityRepository): Option[Double] = {
            entityHolder.getById(entityId) match {
                case en: ValueHolder[_] => en.getValue(name) match {
                    case value: DoubleValue => value.get
                    case _ => None
                }
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "name" -> name
            )
        }
    }
    
}

package core.parts.value.custom

import core.entities.properties.ValueHolder
import core.entities.repositoy.EntityRepository
import core.parts.value.basic.StringValue
import json.JValue

object CustomStringValue {
    
    final case class GetStringValue(entityId: String, name: String) extends StringValue {
        override def get(implicit entityHolder: EntityRepository): Option[String] = {
            entityHolder.getById(entityId) match {
                case en: ValueHolder[_] => en.getValue(name) match {
                    case value: StringValue => value.get
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

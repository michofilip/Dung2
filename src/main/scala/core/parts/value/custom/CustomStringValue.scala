package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.parts.value.basic.StringValue
import core.repository.EntityRepository
import json.JValue

object CustomStringValue {
    
    final case class GetStringValue(entityId: Long, name: String) extends StringValue {
        override def get(implicit entityRepository: EntityRepository): Option[String] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
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

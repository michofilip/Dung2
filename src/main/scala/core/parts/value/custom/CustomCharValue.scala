package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.parts.value.basic.CharValue
import core.repository.EntityRepository
import json.JValue

object CustomCharValue {
    
    final case class GetCharValue(entityId: Long, name: String) extends CharValue {
        override def get(implicit entityRepository: EntityRepository): Option[Char] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: CharValue => value.get
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

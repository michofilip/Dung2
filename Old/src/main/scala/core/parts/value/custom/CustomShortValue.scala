package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.parts.value.basic.ShortValue
import core.repository.EntityRepository
import json.JValue

object CustomShortValue {
    
    final case class GetShortValue(entityId: Long, name: String) extends ShortValue {
        override def get(implicit entityRepository: EntityRepository): Option[Short] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: ShortValue => value.get
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

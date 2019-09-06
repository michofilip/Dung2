package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.parts.value.basic.FloatValue
import core.repository.EntityRepository
import json.JValue

object CustomFloatValue {
    
    final case class GetFloatValue(entityId: Long, name: String) extends FloatValue {
        override def get(implicit entityRepository: EntityRepository): Option[Float] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: FloatValue => value.get
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

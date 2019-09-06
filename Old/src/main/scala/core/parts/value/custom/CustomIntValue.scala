package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.parts.value.basic.IntValue
import core.repository.EntityRepository
import json.JValue

object CustomIntValue {
    
    final case class GetIntValue(entityId: Long, name: String) extends IntValue {
        override def get(implicit entityRepository: EntityRepository): Option[Int] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: IntValue => value.get
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

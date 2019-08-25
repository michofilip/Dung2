package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.parts.value.basic.ByteValue
import core.repository.EntityRepository
import json.JValue

object CustomByteValue {
    
    final case class GetByteValue(entityId: Long, name: String) extends ByteValue {
        override def get(implicit entityRepository: EntityRepository): Option[Byte] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: ByteValue => value.get
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

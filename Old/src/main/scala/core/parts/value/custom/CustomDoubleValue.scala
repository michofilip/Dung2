package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.parts.value.basic.DoubleValue
import core.repository.EntityRepository
import json.JValue

object CustomDoubleValue {
    
    final case class GetDoubleValue(entityId: Long, name: String) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
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

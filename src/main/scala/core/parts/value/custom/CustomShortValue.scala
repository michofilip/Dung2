package core.parts.value.custom

import core.entities.properties.ValueHolder
import core.entities.repositoy.EntityRepository
import core.parts.value.basic.ShortValue
import json.JValue

object CustomShortValue {
    
    final case class GetShortValue(entityId: String, name: String) extends ShortValue {
        override def get(implicit entityRepository: EntityRepository): Option[Short] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder[_] => en.getValue(name) match {
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

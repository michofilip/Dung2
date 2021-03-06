package core.parts.value

import core.entities.traits.properties.ValueProperty
import core.repository.EntityRepository
import value.CharValue

object CustomCharValue {
    
    final case class GetCharValue(entityId: Long, name: String)
                                 (implicit entityRepository: EntityRepository) extends CharValue {
        override def get: Option[Char] = entityRepository.getById(entityId) match {
            case en: ValueProperty => en.getValue(name) match {
                case value: CharValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}

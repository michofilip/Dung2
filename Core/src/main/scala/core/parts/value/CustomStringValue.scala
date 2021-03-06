package core.parts.value

import core.entities.traits.properties.ValueProperty
import core.repository.EntityRepository
import value.StringValue

object CustomStringValue {
    
    final case class GetStringValue(entityId: Long, name: String)
                                   (implicit entityRepository: EntityRepository) extends StringValue {
        override def get: Option[String] = entityRepository.getById(entityId) match {
            case en: ValueProperty => en.getValue(name) match {
                case value: StringValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}

package core.parts.value

import core.entities.traits.properties.ValueHolder
import core.repository.EntityRepository
import value.ShortValue

object CustomShortValue {
    
    final case class GetShortValue(entityId: Long, name: String)
                                  (implicit entityRepository: EntityRepository) extends ShortValue {
        override def get: Option[Short] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: ShortValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}

package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.repository.EntityRepository
import value.IntValue

object CustomIntValue {
    
    final case class GetIntValue(entityId: Long, name: String)
                                (implicit entityRepository: EntityRepository) extends IntValue {
        override def get: Option[Int] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: IntValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}
